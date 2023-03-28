package org.gantry.apiserver.domain;

import javassist.NotFoundException;
import org.gantry.apiserver.persistence.ApplicationRepository;
import org.gantry.apiserver.persistence.ContainerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DockerClientConnectTest {
    @Autowired
    DockerClientConnect dockerClientConnect;

    @Autowired
    ContainerRepository containerRepository;
    @Autowired
    ApplicationRepository applicationRepository;

    Application application = Application.builder()
            .id(1L)
            .image("docker/getting-started")
            .title("hello_docker")
            .build();

    private void saveApplication(){
        applicationRepository.save(application);
    }

    @Test
    @Order(1)
    void run() {
        saveApplication();
        String containerId = dockerClientConnect.run(application);
        Container runContainer = containerRepository.findById(containerId).get();
        Assertions.assertEquals(runContainer.getStatus(), ContainerStatus.of("RUNNING"));

    }

    @Test
    @Order(2)
    void stop() {
        Application runApp = applicationRepository.findById(1L).get();
        String containerId = runApp.getContainer().getId();
        dockerClientConnect.stop(containerId);
        Container stopContainer = containerRepository.findById(containerId).get();
        Assertions.assertEquals(stopContainer.getStatus(), ContainerStatus.of("PAUSED"));
    }

    @Test
    @Order(3)
    void restart() {
        Application stopApp = applicationRepository.findById(1L).get();
        String containerId = stopApp.getContainer().getId();
        dockerClientConnect.restart(containerId);
        Container stopContainer = containerRepository.findById(containerId).get();
        Assertions.assertEquals(stopContainer.getStatus(), ContainerStatus.of("RUNNING"));
    }

    @Test
    @Order(4)
    void remove() {
        Application restartApp = applicationRepository.findById(1L).get();
        String containerId = restartApp.getContainer().getId();
        dockerClientConnect.remove(containerId);

        assertThatThrownBy(()->containerRepository.findById(containerId).orElseThrow(()))
                .isInstanceOf(NoSuchElementException.class);
    }

}