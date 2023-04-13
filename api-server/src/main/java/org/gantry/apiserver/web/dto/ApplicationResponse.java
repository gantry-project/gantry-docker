package org.gantry.apiserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.gantry.apiserver.domain.Application;

@Builder
@Getter
@ToString
public class ApplicationResponse {
    private String title;
    private String image;
    private String containerId;

    public static ApplicationResponse from(Application entity) {
        return ApplicationResponse.builder()
                .title(entity.getTitle())
                .image(entity.getImage())
                .containerId(entity.getContainer().getId())
                .build();
    }
}
