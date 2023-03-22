package org.gantry.apiserver.web.dto;

import lombok.Builder;
import lombok.Data;
import org.gantry.apiserver.domain.ContainerStatus;
import org.gantry.apiserver.domain.ContainerInfo;

@Builder
@Data
public class ContainerDto {
    private long id;
    private long applicationId;
    private ContainerStatus status;

    public static ContainerDto from(ContainerInfo container) {
        return ContainerDto.builder()
                .id(container.getId())
                .applicationId(container.getApplicationId())
                .status(container.getStatus())
                .build();
    }
}
