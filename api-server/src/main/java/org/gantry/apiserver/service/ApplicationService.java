package org.gantry.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.domain.Application;
import org.gantry.apiserver.domain.Container;
import org.gantry.apiserver.domain.docker.DockerClientConnect;
import org.gantry.apiserver.exception.NoSuchApplicationException;
import org.gantry.apiserver.persistence.ApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository repository;

    private final DockerClientConnect docker;

    public List<Application> findAll() {
        return repository.findAll();
    }

    public Optional<Application> findById(Long applicationId) {
        return repository.findById(applicationId);
    }

    @Transactional
    public Container execute(Long applicationId) {
        var application = this.findById(applicationId)
                .orElseThrow(NoSuchApplicationException.with(applicationId));
        String containerId = docker.run(application);
        return docker.getStatus(containerId);
    }

}
