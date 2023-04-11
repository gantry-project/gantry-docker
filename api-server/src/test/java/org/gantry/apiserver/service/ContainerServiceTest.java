package org.gantry.apiserver.service;

import org.gantry.apiserver.domain.Container;
import org.gantry.apiserver.domain.docker.DockerClientConnect;
import org.gantry.apiserver.persistence.ContainerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gantry.apiserver.domain.ContainerStatus.RESTARTING;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ContainerServiceTest {
    @InjectMocks
    private ContainerService service;

    @Mock
    private ContainerRepository repository;

    @Mock
    private DockerClientConnect docker;


    @Test
    void list() {
        given(repository.findAll()).willReturn(List.of(Container.builder().id("test").build()));
        List<Container> list = service.findAll();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getId()).isEqualTo("test");
    }

    @Test
    void restart() {
        String containerId = "test001";
        given(docker.getStatus(containerId)).willReturn(Container.builder().status(RESTARTING).build());
        Container container = service.restart(containerId);
        assertThat(container.getStatus()).isEqualTo(RESTARTING);
    }
}