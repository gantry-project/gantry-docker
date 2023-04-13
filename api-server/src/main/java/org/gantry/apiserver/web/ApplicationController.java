package org.gantry.apiserver.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.web.dto.ApplicationResponse;
import org.gantry.apiserver.service.ApplicationService;
import org.gantry.apiserver.web.dto.ContainerLogResponse;
import org.gantry.apiserver.web.dto.ContainerResponse;
import org.gantry.apiserver.exception.NoSuchApplicationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    private final ApplicationService service;

    @Operation(summary = "List applications to launch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found applications"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @GetMapping()
    public List<ApplicationResponse> list() {
        return service.findAll().stream()
                .map(ApplicationResponse::from)
                .toList();
    }

    @Operation(summary = "Get information of an application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found an application"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @GetMapping("/{applicationId}")
    public ApplicationResponse get(@PathVariable Long applicationId) {
        return service.findById(applicationId)
                .map(ApplicationResponse::from)
                .orElseThrow(NoSuchApplicationException.with(applicationId));
    }

    @Operation(summary = "Launch an application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Launching an application"),
            @ApiResponse(responseCode = "500", description = "Server Error or Connection Error with Docker"),
    })
    @PostMapping("/{applicationId}/execute")
    public ContainerResponse execute(@PathVariable Long applicationId) {
        return ContainerResponse.from(service.execute(applicationId));
    }
}
