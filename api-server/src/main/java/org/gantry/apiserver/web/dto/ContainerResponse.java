package org.gantry.apiserver.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.gantry.apiserver.domain.ContainerStatus;
import org.gantry.apiserver.domain.Container;

@Builder
@Data
@ToString
public class ContainerResponse {
    private String id;
    private long applicationId;
    private ContainerStatus status;

    public static ContainerResponse from(Container container) {
        return ContainerResponse.builder()
                .id(container.getId())
                .applicationId(container.getApplication().getId())
                .status(container.getStatus())
                .build();
    }
}
