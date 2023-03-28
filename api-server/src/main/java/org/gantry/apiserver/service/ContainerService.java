package org.gantry.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.domain.Container;
import org.gantry.apiserver.domain.DockerClientConnect;
import org.gantry.apiserver.persistence.ContainerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.gantry.apiserver.domain.ContainerStatus.RUNNING;

@RequiredArgsConstructor
@Service
public class ContainerService {
    private final ContainerRepository repository;

    private final DockerClientConnect docker;

    public List<Container> findRunningContainers() {
        return repository.findByStatus(RUNNING);
    }

    @Transactional
    public Container stop(String containerId) {
        docker.stop(containerId);
        return docker.getStatus(containerId);
    }

    @Transactional
    public Container remove(String containerId) {
        docker.remove(containerId);
        return docker.getStatus(containerId);
    }

    public Container getStatus(String containerId) {
        return docker.getStatus(containerId);
    }

    @Transactional
    public Container restart(String containerId) {
        docker.restart(containerId);
        return docker.getStatus(containerId);
    }
}
