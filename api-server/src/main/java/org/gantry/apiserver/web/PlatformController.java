package org.gantry.apiserver.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.domain.Platform;
import org.gantry.apiserver.service.PlatformService;
import org.gantry.apiserver.web.dto.PlatformCreateRequest;
import org.gantry.apiserver.web.dto.PlatformResponse;
import org.gantry.apiserver.web.dto.PlatformUpdateRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/platforms")
@RestController
public class PlatformController {

    private final PlatformService service;

    @Operation(summary = "Create a platform")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create a platform"),
            @ApiResponse(responseCode = "404", description = "Not Found the platform"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @PostMapping()
    public PlatformResponse create(@RequestBody PlatformCreateRequest createRequest) {
        Platform newPlatform = createRequest.toPlatform();
        newPlatform.setActive(false);
        Platform platform = service.createPlatform(newPlatform);
        return PlatformResponse.from(platform);
    }

    @Operation(summary = "List platforms information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found platforms"),
            @ApiResponse(responseCode = "404", description = "Not Found a platform"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @GetMapping()
    public List<PlatformResponse> list() {
        return service.findAll().stream().map(PlatformResponse::from).toList();
    }

    @Operation(summary = "Get platform information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found platform"),
            @ApiResponse(responseCode = "404", description = "Not Found a platform"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @GetMapping("/{platformId}")
    public PlatformResponse getPlatform(@PathVariable Long platformId) {
        Platform platform = service.findById(platformId);
        return PlatformResponse.from(platform);
    }

    @Operation(summary = "Update platform information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update platformId information"),
            @ApiResponse(responseCode = "404", description = "Not Found a platformId"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @PutMapping("/{platformId}")
    public PlatformResponse updatePlatform(@PathVariable Long platformId, @RequestBody PlatformUpdateRequest updatePlatform) {
        throwIfMismatchPlatformId(platformId, updatePlatform.getId());
        Platform platform = service.updatePlatform(updatePlatform.toPlatform());
        return PlatformResponse.from(platform);
    }

    @Operation(summary = "Activate the platform")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activate the platform"),
            @ApiResponse(responseCode = "404", description = "Not Found a platform"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @PutMapping("/{platformId}/activate")
    public PlatformResponse activatePlatform(@PathVariable Long platformId) {
        Platform platform = service.activatePlatform(platformId);
        return PlatformResponse.from(platform);
    }

    @Operation(summary = "Delete a platform")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a platform"),
            @ApiResponse(responseCode = "404", description = "Not Found a platform"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @DeleteMapping("/{platformId}")
    public PlatformResponse deletePlatform(@PathVariable Long platformId) {
        Platform platform = service.deletePlatform(platformId);
        return PlatformResponse.from(platform);
    }

    private static void throwIfMismatchPlatformId(long urlId, long bodyId)  {
        if (urlId != bodyId) {
            throw new IllegalArgumentException(
                    "Mismatch request information: %d in URL, %d in Body".formatted(urlId, bodyId));
        }
    }

}
