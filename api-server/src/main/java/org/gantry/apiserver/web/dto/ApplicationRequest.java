package org.gantry.apiserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.gantry.apiserver.domain.Application;

@Builder
@Getter
@ToString
public class ApplicationRequest {
    private String title;
    private String image;
    private String containerId;

    public static ApplicationRequest from(Application entity) {
        return ApplicationRequest.builder()
                .title(entity.getTitle())
                .image(entity.getImage())
                .containerId(entity.getContainer().getId())
                .build();
    }
}
