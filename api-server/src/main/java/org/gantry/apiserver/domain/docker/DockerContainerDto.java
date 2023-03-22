package org.gantry.apiserver.domain.docker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.gantry.apiserver.domain.ContainerInfo;
import org.gantry.apiserver.domain.ContainerStatus;

import java.util.List;
import java.util.Map;

@Data
public class DockerContainerDto {

    @JsonProperty("Id")
    private String id;
    @JsonProperty("Names")
    private List<String> names;
    @JsonProperty("Image")
    private String image;
    @JsonProperty("ImageID")
    private String imageid;
    @JsonProperty("Command")
    private String command;
    @JsonProperty("Created")
    private int created;
    @JsonProperty("State")
    private String state;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Ports")
    private List<Ports> ports;
    @JsonProperty("Labels")
    private Map<String,String> labels;
    @JsonProperty("SizeRw")
    private int sizerw;
    @JsonProperty("SizeRootFs")
    private int sizerootfs;
    @JsonProperty("HostConfig")
    private Hostconfig hostconfig;
    @JsonProperty("NetworkSettings")
    private Networksettings networksettings;
    @JsonProperty("Mounts")
    private List<Mounts> mounts;

    public ContainerInfo toContainerInfo() {
        return ContainerInfo.builder()
                .id(this.id)
                .status(ContainerStatus.valueOf(this.state.toUpperCase()))
                .build();
    }

}