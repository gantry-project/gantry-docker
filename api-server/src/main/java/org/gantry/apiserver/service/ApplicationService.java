package org.gantry.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.domain.Application;
import org.gantry.apiserver.domain.DockerClient;
import org.gantry.apiserver.domain.ContainerInfo;
import org.gantry.apiserver.persistence.ApplicationRepository;
import org.gantry.apiserver.exception.NoSuchApplicationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository repository;

    private final DockerClient docker;

    public List<Application> findAll() {
        return repository.findAll();
    }

    public Optional<Application> findById(Long applicationId) {
        return repository.findById(applicationId);
    }

    public ContainerInfo execute(Long applicationId) {
        var application = this.findById(applicationId)
                .orElseThrow(() -> new NoSuchApplicationException());
        long containerId = docker.run(application);
        return docker.getStatus(containerId);
    }

    public ContainerInfo stop(Long containerId) {
        docker.stop(containerId);
        return docker.getStatus(containerId);
    }

    public ContainerInfo remove(Long containerId) {
        docker.remove(containerId);
        return docker.getStatus(containerId);
    }

    public ContainerInfo getStatus(Long containerId) {
        return docker.getStatus(containerId);
    }

    public ContainerInfo restart(Long containerId) {
        docker.restart(containerId);
        return docker.getStatus(containerId);
    }

}
