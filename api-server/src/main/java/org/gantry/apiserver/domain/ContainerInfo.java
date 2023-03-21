package org.gantry.apiserver.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContainerInfo {
    private long id;
    private long applicationId;
    private ContainerStatus status;
}
