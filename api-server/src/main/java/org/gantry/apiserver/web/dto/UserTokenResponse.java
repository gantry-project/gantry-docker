package org.gantry.apiserver.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.gantry.apiserver.domain.Authority;
import org.gantry.apiserver.domain.User;

@Builder
@Data
@ToString
public class UserTokenResponse {
    private long id;
    private String username;
    private String email;
    private boolean enabled;
    private Authority authority;
    private String accessToken;

    public static UserTokenResponse from(User user) {
        return UserTokenResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .authority(user.getAuthority())
                .build();
    }
}
