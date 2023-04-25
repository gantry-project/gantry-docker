package org.gantry.apiserver.persistence;

import org.gantry.apiserver.domain.DockerNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkRepository extends JpaRepository<DockerNetwork, String> {
}
