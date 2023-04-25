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
        long appId = container.getApplication() == null ? 0l : container.getApplication().getId();
        return ContainerResponse.builder()
                .id(container.getId())
                .applicationId(appId)
                .status(container.getStatus())
                .build();
    }
}
