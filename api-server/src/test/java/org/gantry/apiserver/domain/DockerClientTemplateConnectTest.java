package org.gantry.apiserver.domain;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import org.gantry.apiserver.domain.docker.DockerClientConnect;
import org.gantry.apiserver.domain.docker.DockerClientFactory;
import org.gantry.apiserver.persistence.ApplicationRepository;
import org.gantry.apiserver.persistence.ContainerRepository;
import org.gantry.apiserver.persistence.PlatformRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.gantry.apiserver.domain.ContainerStatus.RUNNING;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class DockerClientTemplateConnectTest {
    private DockerClientConnect dockerClientConnect;

    @Mock
    private DockerClient client;

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    private static Application testApplication;

    private static Container testContainer;

    private static long applicationId;

    @BeforeAll
    static void createFixture() {
        testContainer = Container.builder()
                .id("fx_ctn_test0001")
                .status(RUNNING)
                .build();
        testApplication = Application.builder()
                .image("docker/getting-started")
                .title("hello_docker")
                .build();

        testContainer.setApplication(testApplication);
        testApplication.setContainer(testContainer);
    }

    @BeforeEach
    void saveApplication(){
        testApplication = applicationRepository.save(testApplication);
        containerRepository.save(testContainer);
        applicationId = testApplication.getId();

        DockerClientFactory factory = mock(DockerClientFactory.class);
        given(factory.getInstance()).willReturn(client);
        dockerClientConnect = new DockerClientConnect(containerRepository, platformRepository, factory);
    }

    @Test
    void run() {
        CreateContainerCmd createCmd = mock(CreateContainerCmd.class);
        given(createCmd.exec()).willReturn(new CreateContainerResponse() {{setId("test001");}});
        given(client.createContainerCmd(anyString())).willReturn(createCmd);
        given(client.startContainerCmd(anyString())).willReturn(mock(StartContainerCmd.class));

        String containerId = dockerClientConnect.run(testApplication);
        
        assertThat(containerId).isEqualTo("test001");
        verify(createCmd, times(1)).exec();
    }

//    @Test
    void process() throws InterruptedException, IOException {
        dockerClientConnect.log(testContainer.getId());
    }

    @Test
    void stop() {
        StopContainerCmd stopCmd = mock(StopContainerCmd.class);
        given(client.stopContainerCmd(anyString())).willReturn(stopCmd);

        Application runApp = applicationRepository.findById(applicationId).get();
        String containerId = runApp.getContainer().getId();
        dockerClientConnect.stop(containerId);

        assertThat(containerId).isEqualTo("fx_ctn_test0001");
        verify(stopCmd, times(1)).exec();
    }

    @Test
    void restart() {
        RestartContainerCmd restartCmd = mock(RestartContainerCmd.class);
        given(client.restartContainerCmd(anyString())).willReturn(restartCmd);

        Application stopApp = applicationRepository.findById(applicationId).get();
        String containerId = stopApp.getContainer().getId();
        String restartedContainerId = dockerClientConnect.restart(containerId);

        assertThat(restartedContainerId).isEqualTo("fx_ctn_test0001");
        verify(restartCmd, times(1)).exec();

    }

    @Test
    void remove() {
        given(client.removeContainerCmd(anyString())).willReturn(mock(RemoveContainerCmd.class));

        Application restartApp = applicationRepository.findById(applicationId).get();
        String containerId = restartApp.getContainer().getId();
        dockerClientConnect.remove(containerId);

        assertThatThrownBy(() -> containerRepository
                .findById(containerId)
                .orElseThrow(NoSuchElementException::new)
        ).isInstanceOf(NoSuchElementException.class);
    }

}