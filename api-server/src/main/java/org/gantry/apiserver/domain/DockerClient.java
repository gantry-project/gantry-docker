package org.gantry.apiserver.domain;

import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.domain.docker.DockerContainerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DockerClient {
    private final RestTemplate restTemplate;

    @Value("${docker.tcp.host}")
    private String DOCKER_HOST;

    public String run(Application application) {
        return "0";
    }

    public void stop(String containerId) {
    }

    public void remove(String containerId) {
    }

    public ContainerInfo getStatus(String containerId) {
        return null;
    }

    public ContainerInfo restart(String containerId) {
        return null;
    }

    public List<ContainerInfo> list() {
        DockerContainerDto[] containers = restTemplate.getForObject(DOCKER_HOST + "/containers/json", DockerContainerDto[].class);
        return Arrays.stream(containers).map(DockerContainerDto::toContainerInfo).toList();
    }
}
