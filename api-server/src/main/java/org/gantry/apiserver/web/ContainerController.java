package org.gantry.apiserver.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.domain.Container;
import org.gantry.apiserver.service.ContainerService;
import org.gantry.apiserver.web.dto.ContainerRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/containers")
@RestController
public class ContainerController {

    private final ContainerService service;

    @Operation(summary = "List containers from Docker")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found containers"),
            @ApiResponse(responseCode = "500", description = "Server Error or Connection Error with Docker"),
    })
    @GetMapping
    public List<ContainerRequest> list() {
        return service.findAll().stream()
                .map(ContainerRequest::from)
                .toList();
    }

    @Operation(summary = "Get information and state of a container")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found container information"),
            @ApiResponse(responseCode = "404", description = "Not Found a container"),
            @ApiResponse(responseCode = "500", description = "Server Error or Connection Error with Docker"),
    })
    @GetMapping("/{containerId}")
    public ContainerRequest get(@PathVariable String containerId) {
        Container container = service.findById(containerId);
        return ContainerRequest.from(container);
    }

    @Operation(summary = "Restart a container")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restarting a container"),
            @ApiResponse(responseCode = "404", description = "Not Found the container"),
            @ApiResponse(responseCode = "500", description = "Server Error or Connection Error with Docker"),
    })
    @PostMapping("/{containerId}/restart")
    public ContainerRequest restart(@PathVariable String containerId) {
        Container container = service.restart(containerId);
        return ContainerRequest.from(container);
    }

    @Operation(summary = "Stop a container")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stopping a container"),
            @ApiResponse(responseCode = "404", description = "Not Found the container"),
            @ApiResponse(responseCode = "500", description = "Server Error or Connection Error with Docker"),
    })
    @PostMapping("/{containerId}/stop")
    public ContainerRequest stop(@PathVariable String containerId) {
        Container container = service.stop(containerId);
        return ContainerRequest.from(container);
    }

    @Operation(summary = "Remove a container")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Removing a container"),
            @ApiResponse(responseCode = "404", description = "Not Found the container"),
            @ApiResponse(responseCode = "500", description = "Server Error or Connection Error with Docker"),
    })
    @PostMapping("/{containerId}/remove")
    public ContainerRequest remove(@PathVariable String containerId) {
        Container container = service.remove(containerId);
        return ContainerRequest.from(container);
    }
}
