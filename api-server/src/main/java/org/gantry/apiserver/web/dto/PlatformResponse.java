package org.gantry.apiserver.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gantry.apiserver.domain.Platform;
import org.gantry.apiserver.domain.PlatformType;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PlatformResponse {
    private Long id;
    private PlatformType type;
    private String url;
    private String name;
    private String description;
    private boolean active;

    public static PlatformResponse from(Platform platform) {
        return PlatformResponse.builder()
                .id(platform.getId())
                .type(platform.getType())
                .url(platform.getUrl())
                .name(platform.getName())
                .description(platform.getDescription())
                .active(platform.isActive())
                .build();
    }

    public Platform toPlatform() {
        return Platform.builder()
                .id(id)
                .type(type)
                .url(url)
                .name(name)
                .description(description)
                .active(active)
                .build();
    }
}
