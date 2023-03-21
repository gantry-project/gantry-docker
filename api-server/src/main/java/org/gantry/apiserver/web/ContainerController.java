package org.gantry.apiserver.web;

import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.service.ApplicationService;
import org.gantry.apiserver.web.dto.ContainerDto;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/containers")
@RestController
public class ContainerController {

    private final ApplicationService service;

    @GetMapping("/{containerId}/status")
    public ContainerDto status(@PathVariable Long containerId) {
        var container = service.getStatus(containerId);
        return ContainerDto.from(container);
    }

    @PostMapping("/{containerId}/stop")
    public ContainerDto stop(@PathVariable Long containerId) {
        var container = service.stop(containerId);
        return ContainerDto.from(container);
    }

    @PostMapping("/{containerId}/remove")
    public ContainerDto remove(@PathVariable Long containerId) {
        var container = service.remove(containerId);
        return ContainerDto.from(container);
    }
}
