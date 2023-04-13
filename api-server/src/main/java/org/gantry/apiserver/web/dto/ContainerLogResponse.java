package org.gantry.apiserver.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.gantry.apiserver.domain.Container;
import org.gantry.apiserver.domain.ContainerStatus;
@Builder
@Data
@ToString
public class ContainerLogResponse {
    private String id;
    private long applicationId;
    private ContainerStatus status;
    private String log;

    public static ContainerLogResponse from(Container container) {
        return ContainerLogResponse.builder()
                .id(container.getId())
                .applicationId(container.getApplication().getId())
                .status(container.getStatus())
                .log(container.getLog())
                .build();
    }
}
