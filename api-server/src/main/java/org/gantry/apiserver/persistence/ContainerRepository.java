package org.gantry.apiserver.persistence;

import org.gantry.apiserver.domain.Container;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerRepository extends JpaRepository<Container, String> {
}
