package org.gantry.apiserver.service;

import org.gantry.apiserver.domain.Application;
import org.gantry.apiserver.domain.docker.DockerClientConnect;
import org.gantry.apiserver.persistence.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    private ApplicationRepository repository;

    @Mock
    private DockerClientConnect docker;

    private ApplicationService service;

    @BeforeEach
    public void before() {
        service = new ApplicationService(repository, docker);
    }

    @Test
    void list() {
        given(repository.findAll()).willReturn(List.of(Application.builder().title("test").build()));
        List<Application> list = service.findAll();
        assertThat(list.get(0).getTitle()).isEqualTo("test");
    }
}