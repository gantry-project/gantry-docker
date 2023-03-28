package org.gantry.apiserver.domain;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.SeBootstrap;
import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.persistence.ContainerRepository;
import org.gantry.apiserver.web.dto.ContainerDto;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.gantry.apiserver.domain.ContainerStatus.*;

// TODO: Not implemented yet.
@Component
@RequiredArgsConstructor
public class DockerClientConnect {

    private final DockerClient dockerClient;
    private final ContainerRepository containerRepository;
    @Transactional
    public String run(Application application) { // run = create + start
        CreateContainerResponse createContainer = dockerClient.createContainerCmd(application.getImage())
                //.withExposedPorts() 컨테이너 ExposedPorts 설정
                .exec();
        dockerClient.startContainerCmd(createContainer.getId()).exec();

        containerRepository.save(Container.builder()
                .id(createContainer.getId())
                .application(application)
                .status(of("RUNNING")).build());

        return createContainer.getId();
    }
    @Transactional
    public void stop(String containerId) {
        Container container = findContainerId(containerId);
        dockerClient.pauseContainerCmd(container.getId()).exec();
        changeStatus(container, "PAUSED");
    }

    private Container findContainerId(String containerId) {
        return containerRepository.findById(containerId).orElseThrow(NotFoundException::new);
    }
    private void changeStatus(Container container, String status){
        container.setStatus(of(status));// 엔티티의 setter는 추후 변경
    }
    @Transactional
    public void remove(String containerId) {
        Container container = findContainerId(containerId);
        dockerClient.stopContainerCmd(container.getId()).exec();
        containerRepository.delete(container);
        changeStatus(container, "REMOVING");

    }
    @Transactional
    public String restart(String containerId) {
        Container container = findContainerId(containerId);
        dockerClient.restartContainerCmd(container.getId()).exec();
        changeStatus(container, "RUNNING");
        return containerId;
    }

    public Container getStatus(String containerId) {
        return findContainerId(containerId);
    }

}
