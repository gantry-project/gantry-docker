package org.gantry.apiserver.persistence;

import org.gantry.apiserver.domain.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
    Optional<Platform> findById(Long id);

    List<Platform> findByActive(Boolean active);
}
