package org.gantry.apiserver.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.web.dto.ApplicationRequest;
import org.gantry.apiserver.service.ApplicationService;
import org.gantry.apiserver.web.dto.ContainerRequest;
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
    public List<ApplicationRequest> list() {
        return service.findAll().stream()
                .map(ApplicationRequest::from)
                .toList();
    }

    @Operation(summary = "Get information of an application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found an application"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @GetMapping("/{applicationId}")
    public ApplicationRequest get(@PathVariable Long applicationId) {
        return service.findById(applicationId)
                .map(ApplicationRequest::from)
                .orElseThrow(NoSuchApplicationException.with(applicationId));
    }

    @Operation(summary = "Launch an application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Launching an application"),
            @ApiResponse(responseCode = "500", description = "Server Error or Connection Error with Docker"),
    })
    @PostMapping("/{applicationId}/execute")
    public ContainerRequest execute(@PathVariable Long applicationId) {
        return ContainerRequest.from(service.execute(applicationId));
    }
}
