package org.gantry.apiserver.domain;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// TODO: Not implemented yet.
@Component
@RequiredArgsConstructor
public class DockerClientConnect {

    private final DockerClient dockerClient;

    public String run(Application application) { // run = create + start
        CreateContainerResponse createContainer = dockerClient.createContainerCmd(application.getTitle())
                .withHostName("ipAddress")
                //.withExposedPorts() 컨테이너 ExposedPorts 설정
                .exec();

        dockerClient.startContainerCmd(createContainer.getId()).exec();

        return createContainer.getId();
    }

    public void stop(String containerId) {
        dockerClient.stopContainerCmd(containerId).exec();
    }

    public void remove(String containerId) {
        dockerClient.killContainerCmd(containerId).exec();
    }

    public String restart(String containerId){
        dockerClient.restartContainerCmd(containerId).exec();
        return containerId;
    }

    public ContainerInfo getStatus(String containerId) {
        Container findContainer = dockerClient.listContainersCmd().withShowAll(true).exec()
                .stream().filter(container -> container.getId().equals(containerId)).findFirst().orElseThrow(); // TODO NotFound Exception, ContainerInfo 내에 Application ID 확인

        return null;
    }

}
