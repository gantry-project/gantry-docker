package org.gantry.apiserver.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.gantry.apiserver.domain.Application;

@Builder
@Getter
@ToString
public class ApplicationDto {
    private String title;
    private String image;
    private String containerId;

    public static ApplicationDto from(Application entity) {
        return ApplicationDto.builder()
                .title(entity.getTitle())
                .image(entity.getImage())
                .containerId(entity.getContainer().getId())
                .build();
    }
}
