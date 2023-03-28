package org.gantry.apiserver.persistence;

import org.gantry.apiserver.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
