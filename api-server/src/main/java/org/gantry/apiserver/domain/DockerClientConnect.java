package org.gantry.apiserver.domain;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.gantry.apiserver.exception.NoSuchContainerException;
import org.gantry.apiserver.persistence.ContainerRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.gantry.apiserver.domain.ContainerStatus.*;

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
                .status(RUNNING).build());

        return createContainer.getId();
    }
    @Transactional
    public void stop(String containerId) {
        Container container = findContainerId(containerId);
        dockerClient.pauseContainerCmd(container.getId()).exec();
        changeStatus(container, PAUSED);
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
        changeStatus(container, REMOVING);

    }
    @Transactional
    public String restart(String containerId) {
        Container container = findContainerId(containerId);
        dockerClient.restartContainerCmd(container.getId()).exec();
        changeStatus(container, RESTARTING);
        return containerId;
    }

    public Container getStatus(String containerId) {
        return findContainerId(containerId);
    }

    public List<Container> containerList() {
        return dockerClient.listContainersCmd().exec().stream().map(c -> {
            Container container = Container.builder()
                                        .id(c.getId())
                                        .status(ContainerStatus.of(c.getState()))
                                        .build();
            Application application = findContainerId(c.getId()).getApplication();
            container.setApplication(application);
            return container;
        }).toList();
    }

}