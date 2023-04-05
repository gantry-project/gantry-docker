package org.gantry.apiserver.web.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.gantry.apiserver.domain.Authority;
import org.gantry.apiserver.domain.Container;
import org.gantry.apiserver.domain.ContainerStatus;
import org.gantry.apiserver.domain.User;

@Builder
@Data
@ToString
public class UserDto {
    private long id;
    private String username;
    private String email;
    private boolean enabled;
    private Authority authority;
    private String accessToken;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .authority(user.getAuthority())
                .build();
    }
}
