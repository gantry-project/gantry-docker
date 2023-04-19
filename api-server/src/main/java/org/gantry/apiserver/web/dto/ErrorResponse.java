package org.gantry.apiserver.web.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Builder
@Data
public class ErrorResponse {
    private HttpStatus status;
    private String uri;
    private String message;
    private String detail;
}
