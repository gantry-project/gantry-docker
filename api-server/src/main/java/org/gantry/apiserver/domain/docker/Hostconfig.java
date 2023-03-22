package org.gantry.apiserver.domain.docker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Hostconfig {

    @JsonProperty("NetworkMode")
    private String networkmode;

}