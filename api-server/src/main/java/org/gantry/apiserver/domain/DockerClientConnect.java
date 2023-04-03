package org.gantry.apiserver.domain;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import jakarta.ws.rs.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.gantry.apiserver.exception.NoSuchContainerException;
import org.gantry.apiserver.persistence.ContainerRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.gantry.apiserver.domain.ContainerStatus.of;


@Component
@RequiredArgsConstructor
public class DockerClientConnect {

    @Setter
    private DockerClient dockerClient;

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

    public List<Container> containerList() {
        return dockerClient.listContainersCmd().exec().stream().map(c -> {
            Container container = Container.builder()
                                        .id(c.getId())
                                        .build();
            Application application = findContainerId(c.getId()).getApplication();
            container.setApplication(application);
            return container;
        }).toList();
    }

}
