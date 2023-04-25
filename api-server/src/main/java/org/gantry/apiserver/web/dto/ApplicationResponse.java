package org.gantry.apiserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.gantry.apiserver.domain.Application;

@Builder
@Getter
@ToString
public class ApplicationResponse {
    private long id;
    private String title;
    private String image;

    public static ApplicationResponse from(Application entity) {
        return ApplicationResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .image(entity.getImage())
                .build();
    }
}
