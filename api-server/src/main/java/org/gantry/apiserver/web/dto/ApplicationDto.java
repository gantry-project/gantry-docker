package org.gantry.apiserver.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.gantry.apiserver.domain.Application;

@Builder
@Getter
public class ApplicationDto {
    private String title;
    private String image;

    public static ApplicationDto from(Application entity) {
        return ApplicationDto.builder()
                .title(entity.getTitle())
                .image(entity.getImage())
                .build();
    }
}
