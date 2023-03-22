package org.gantry.apiserver.domain.docker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Ports {

    @JsonProperty("PrivatePort")
    private int privateport;
    @JsonProperty("PublicPort")
    private int publicport;
    @JsonProperty("Type")
    private String type;

}