package org.gantry.apiserver.web.dto;

import lombok.Builder;
import lombok.Data;
import org.gantry.apiserver.domain.Container;
import org.gantry.apiserver.domain.DockerNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class NetworkResponse {
    private String id;
    private String name;
    private List<ContainerResponse> containers;
    @Builder
    public NetworkResponse(String id, String name, List<ContainerResponse> containers) {
        this.id = id;
        this.name = name;
        this.containers = containers;
    }
    public static NetworkResponse from(DockerNetwork network){
        return NetworkResponse.builder()
                .id(network.getId())
                .name(network.getName())
                .containers(network.getContainers().stream().map(container -> ContainerResponse.builder()
                        .id(container.getId())
                        .status(container.getStatus())
                        .applicationId(container.getApplication().getId())
                        .build()).collect(Collectors.toList())).build();
    }
}
