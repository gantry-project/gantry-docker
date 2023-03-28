package org.gantry.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.domain.Application;
import org.gantry.apiserver.domain.DockerClientConnect;
import org.gantry.apiserver.domain.Container;
import org.gantry.apiserver.persistence.ApplicationRepository;
import org.gantry.apiserver.exception.NoSuchApplicationException;
import org.gantry.apiserver.web.dto.ContainerDto;
import org.springframework.stereotype.Service;
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

    public Container execute(Long applicationId) {
        var application = this.findById(applicationId)
                .orElseThrow(() -> new NoSuchApplicationException());
        String containerId = docker.run(application);
        return docker.getStatus(containerId);
    }

    public Container stop(String containerId) {
        docker.stop(containerId);
        return docker.getStatus(containerId);
    }

    public Container remove(String containerId) {
        docker.remove(containerId);
        return docker.getStatus(containerId);
    }

    public Container getStatus(String containerId) {
        return docker.getStatus(containerId);
    }
}
