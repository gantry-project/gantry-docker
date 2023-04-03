package org.gantry.apiserver.persistence;

import org.gantry.apiserver.domain.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ApplicationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicationRepository repository;

    @BeforeEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    void findAll() {
        repository.save(Application.builder().title("test").image("repo/repo:latest").build());
        repository.save(Application.builder().title("test2").image("repo/repo2:latest").build());
        List<Application> list = repository.findAll();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getTitle()).isEqualTo("test");
    }

    @Test
    void findOne() {
        repository.save(Application.builder().title("test1").image("repo/repo:latest").build());
        List<Application> list = repository.findAll();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getTitle()).isEqualTo("test1");
    }

}