package org.gantry.apiserver.domain.docker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Networksettings {

    @JsonProperty("Networks")
    private Networks networks;

}