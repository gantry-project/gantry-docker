package org.gantry.apiserver.domain.docker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Mounts {

    @JsonProperty("Name")
    private String name;
    @JsonProperty("Source")
    private String source;
    @JsonProperty("Destination")
    private String destination;
    @JsonProperty("Driver")
    private String driver;
    @JsonProperty("Mode")
    private String mode;
    @JsonProperty("RW")
    private boolean rw;
    @JsonProperty("Propagation")
    private String propagation;

}