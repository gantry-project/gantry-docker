package org.gantry.apiserver.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContainerInfo {
    private String id;
    private long applicationId;
    private ContainerStatus status;
}
