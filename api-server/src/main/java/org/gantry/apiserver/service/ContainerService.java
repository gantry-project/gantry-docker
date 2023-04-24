package org.gantry.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.domain.Container;
import org.gantry.apiserver.domain.ContainerStatus;
import org.gantry.apiserver.domain.docker.DockerClientConnect;
import org.gantry.apiserver.persistence.ContainerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContainerService {
    private final ContainerRepository repository;

    private final DockerClientConnect docker;

    public List<Container> findAll() {
        return repository.findAll();
    }

    public Container findById(String containerId) {
        return docker.getStatus(containerId);
    }

    @Transactional
    public Container restart(String containerId) {
        docker.restart(containerId);
        return docker.getStatus(containerId);
    }

    @Transactional
    public Container stop(String containerId) {
        docker.stop(containerId);
        return docker.getStatus(containerId);
    }

    @Transactional
    public Container remove(String containerId) {
        docker.remove(containerId);
        return Container.builder()
                .id(containerId)
                .status(ContainerStatus.REMOVING)
                .build();
    }

    public String log(String containerId) {
        try {
            return docker.log(containerId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
