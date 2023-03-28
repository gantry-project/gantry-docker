package org.gantry.apiserver.domain;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import org.gantry.apiserver.persistence.ApplicationRepository;
import org.gantry.apiserver.persistence.ContainerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.gantry.apiserver.domain.ContainerStatus.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@Import(DockerClientConnect.class)
@ExtendWith(MockitoExtension.class)
@DataJpaTest
class DockerClientConnectTest {
    @InjectMocks
    @Autowired
    private DockerClientConnect dockerClientConnect;

    @Mock
    private DockerClient client;

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    private static Application testApplication;

    private static Container testContainer;

    private static long applicationId;

    @BeforeAll
    static void createFixture() {
        testContainer = Container.builder()
                .id("testid0001")
                .status(RUNNING)
                .build();
        testApplication = Application.builder()
                .image("docker/getting-started")
                .title("hello_docker")
                .container(testContainer)
                .build();

        testContainer.setApplication(testApplication);
    }

    @BeforeEach
    void saveApplication(){
        testApplication = applicationRepository.save(testApplication);
        applicationId = testApplication.getId();
    }

    @Test
    void run() {
        CreateContainerCmd createCmd = mock(CreateContainerCmd.class);
        given(createCmd.exec()).willReturn(new CreateContainerResponse() {{setId("testid");}});
        given(client.createContainerCmd(anyString())).willReturn(createCmd);
        given(client.startContainerCmd(anyString())).willReturn(mock(StartContainerCmd.class));

        String containerId = dockerClientConnect.run(testApplication);
        
        Container runContainer = containerRepository.findById(containerId).get();
        Assertions.assertEquals(RUNNING, runContainer.getStatus());

    }

    @Test
    void stop() {
        given(client.pauseContainerCmd(anyString())).willReturn(mock(PauseContainerCmd.class));

        Application runApp = applicationRepository.findById(applicationId).get();
        String containerId = runApp.getContainer().getId();
        dockerClientConnect.stop(containerId);

        Container stopContainer = containerRepository.findById(containerId).get();
        Assertions.assertEquals(PAUSED, stopContainer.getStatus());
    }

    @Test
    void restart() {
        given(client.restartContainerCmd(anyString())).willReturn(mock(RestartContainerCmd.class));

        Application stopApp = applicationRepository.findById(applicationId).get();
        String containerId = stopApp.getContainer().getId();
        dockerClientConnect.restart(containerId);

        Container stopContainer = containerRepository.findById(containerId).get();
        Assertions.assertEquals(RESTARTING, stopContainer.getStatus());
    }

    @Test
    void remove() {
        given(client.stopContainerCmd(anyString())).willReturn(mock(StopContainerCmd.class));

        Application restartApp = applicationRepository.findById(applicationId).get();
        String containerId = restartApp.getContainer().getId();
        dockerClientConnect.remove(containerId);

        assertThatThrownBy(() -> {
            containerRepository
                    .findById(containerId)
                    .orElseThrow(NoSuchElementException::new);
        }).isInstanceOf(NoSuchElementException.class);
    }

}