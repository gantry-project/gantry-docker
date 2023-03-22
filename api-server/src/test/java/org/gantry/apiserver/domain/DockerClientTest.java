package org.gantry.apiserver.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DockerClientTest {

    @Autowired
    private DockerClient docker;

    @Test
    void getProcessList() {
        List<ContainerInfo> containers = docker.list();
        assertThat(containers).isNotEmpty();
    }

    @Test
    void getStatus() {
//        String containerId = "9ac1a1ee85f7";
//        ContainerInfo container = docker.getStatus(containerId);
//        assertThat(container).isNotNull();
//        assertThat(container.getId()).isEqualTo(containerId);
    }

    @Test
    void run() {
    }

    @Test
    void stop() {
    }

    @Test
    void remove() {
    }

    @Test
    void restart() {
    }
}