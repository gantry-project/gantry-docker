package org.gantry.apiserver.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ResetPasswordRequest {
    private Long id;
    private String password;
    private String confirmPassword;
}
