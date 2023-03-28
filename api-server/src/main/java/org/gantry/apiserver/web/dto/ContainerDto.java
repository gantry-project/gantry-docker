package org.gantry.apiserver.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.gantry.apiserver.domain.ContainerStatus;
import org.gantry.apiserver.domain.Container;

@Builder
@Data
@ToString
public class ContainerDto {
    private String id;
    private long applicationId;
    private ContainerStatus status;

    public static ContainerDto from(Container container) {
        return ContainerDto.builder()
                .id(container.getId())
                .applicationId(container.getApplication().getId())
                .status(container.getStatus())
                .build();
    }
}
