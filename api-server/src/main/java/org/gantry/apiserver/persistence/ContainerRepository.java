package org.gantry.apiserver.persistence;

import org.gantry.apiserver.domain.Container;
import org.gantry.apiserver.domain.ContainerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContainerRepository extends JpaRepository<Container, String> {
    List<Container> findByStatus(ContainerStatus status);
}
