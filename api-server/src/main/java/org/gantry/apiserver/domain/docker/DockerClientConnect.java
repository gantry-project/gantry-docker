package org.gantry.apiserver.domain.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import jakarta.ws.rs.NotFoundException;
import lombok.Setter;
import org.gantry.apiserver.domain.*;
import org.gantry.apiserver.exception.NoSuchContainerException;
import org.gantry.apiserver.exception.NoSuchPlatformException;
import org.gantry.apiserver.persistence.ContainerRepository;
import org.gantry.apiserver.persistence.PlatformRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.gantry.apiserver.domain.ContainerStatus.of;



@Component
public class DockerClientConnect {

    @Setter
    private DockerClient dockerClient;
    private final DockerClientFactory dockerClientFactory;
    private final ContainerRepository containerRepository;
    private final PlatformRepository platformRepository;


    public DockerClientConnect(ContainerRepository containerRepository, PlatformRepository platformRepository, DockerClientFactory dockerClientFactory) {
        this.containerRepository = containerRepository;
        this.platformRepository = platformRepository;
        this.dockerClientFactory = dockerClientFactory;
        this.dockerClient = dockerClientFactory.getInstance();
    }

    @Transactional
    public String run(Application application) { // run = create + start
        CreateContainerResponse createContainer = dockerClient.createContainerCmd(application.getImage())
                //.withExposedPorts() 컨테이너 ExposedPorts 설정
                .exec();
        dockerClient.startContainerCmd(createContainer.getId()).exec();

        containerRepository.save(Container.builder()
                .id(createContainer.getId())
                .application(application)
                .build());

        return createContainer.getId();
    }
    @Transactional
    public void stop(String containerId) {
        Container container = findContainerId(containerId);
        dockerClient.pauseContainerCmd(container.getId()).exec();
    }

    private Container findContainerId(String containerId) {
        return containerRepository.findById(containerId).orElseThrow(NoSuchContainerException.with(containerId));
    }

    private void changeStatus(Container container, ContainerStatus status){
        container.setStatus(status);// 엔티티의 setter 는 추후 변경
    }

    @Transactional
    public void remove(String containerId) {
        Container container = findContainerId(containerId);
        dockerClient.stopContainerCmd(container.getId()).exec();
        containerRepository.delete(container);

    }
    @Transactional
    public String restart(String containerId) {
        Container container = findContainerId(containerId);
        dockerClient.restartContainerCmd(container.getId()).exec();
        return containerId;
    }

    public Container getStatus(String containerId) {
        Container findContainer = containerRepository.findById(containerId).orElseThrow(NotFoundException::new);
        List<com.github.dockerjava.api.model.Container> containers = dockerClient.listContainersCmd().withShowAll(true).exec();
        com.github.dockerjava.api.model.Container dockerContainer = containers.stream().filter(container -> container.getId().equals(containers)).findFirst().orElseThrow();

        findContainer.setStatus(of(dockerContainer.getStatus()));
        return findContainer;
    }

    public void refreshDockerClient() {
        Platform platform = platformRepository.findByActiveTrueAndType(PlatformType.DOCKER)
                .orElseThrow(NoSuchPlatformException.noActiveDockerPlatform());
        DockerClient client = dockerClientFactory.refreshAndGetInstanceWith(() -> platform.getUrl());
        this.setDockerClient(client);
    }
}
