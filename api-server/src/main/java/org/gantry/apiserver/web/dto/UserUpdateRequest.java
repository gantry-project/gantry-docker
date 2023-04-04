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
public class UserUpdateRequest {
    private Long id;
    private String username;
    private String email;
    private Authority authority;

    public static UserUpdateRequest from(User user) {
        return UserUpdateRequest.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .authority(user.getAuthority())
                .build();
    }

    public User toUser() {
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .authority(authority)
                .build();
    }
}
