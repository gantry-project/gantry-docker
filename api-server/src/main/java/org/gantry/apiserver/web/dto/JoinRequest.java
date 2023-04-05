package org.gantry.apiserver.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gantry.apiserver.domain.User;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JoinRequest {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    public User toUser() {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
    public static JoinRequest fromUser(User user) {
        return JoinRequest.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .confirmPassword(user.getPassword())
                .build();
    }
}
