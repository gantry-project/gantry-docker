package org.gantry.apiserver.domain;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Network;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gantry.apiserver.web.dto.NetworkResponse;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DockerNetwork {
    @Id
    @Column(name = "network_id")
    private String id;

    private String name;

    @OneToMany(mappedBy = "network")
    private List<Container> containers;

    public String create(DockerClient dockerClient){
        List<Network> networks = dockerClient.listNetworksCmd().withNameFilter(this.name).exec();
        if (networks.isEmpty()) {
            CreateNetworkResponse networkResponse = dockerClient
                    .createNetworkCmd()
                    .withName(this.name)
                    .withAttachable(true)
                    .withDriver("bridge").exec();
            System.out.printf("Network %s created...\n", networkResponse.getId());
        }
        return this.name;
    }

    public List<String> run(DockerClient dockerClient){
        final List<String> containerId = new ArrayList<>();

        containers.forEach(container -> {
            CreateContainerResponse createContainer = dockerClient.createContainerCmd(container.getId())
                    .withHostConfig(HostConfig.newHostConfig()
                            .withNetworkMode(this.name)
                            .withAutoRemove(true))
                    .exec();
            dockerClient.startContainerCmd(container.getId()).exec();
            containerId.add(container.getId());
        });
        return containerId;
    }

    public void stop(DockerClient dockerClient){
        containers.forEach(container -> {
            dockerClient.pauseContainerCmd(container.getId()).exec();
        });
    }
    public void remove(DockerClient dockerClient){
        dockerClient.removeNetworkCmd(this.id);
    }

}
