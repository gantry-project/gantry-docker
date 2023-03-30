package org.gantry.apiserver.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @Operation(summary = "Heath Check API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check if the server is running"),
            @ApiResponse(responseCode = "500", description = "Server is running, but an internal error has occurred"),
    })
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
