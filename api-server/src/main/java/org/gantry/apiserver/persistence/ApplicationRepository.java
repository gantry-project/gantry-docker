package org.gantry.apiserver.persistence;

import org.gantry.apiserver.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
