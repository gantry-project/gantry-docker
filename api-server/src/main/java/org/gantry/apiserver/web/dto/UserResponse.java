package org.gantry.apiserver.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gantry.apiserver.domain.Authority;
import org.gantry.apiserver.domain.User;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private boolean enabled;
    private Authority authority;

    public User toUser() {
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .authority(authority)
                .enabled(enabled)
                .build();
    }
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .authority(user.getAuthority())
                .enabled(user.isEnabled())
                .build();
    }
}
