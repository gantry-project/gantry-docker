package org.gantry.apiserver.web;

import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.domain.Container;
import org.gantry.apiserver.service.ContainerService;
import org.gantry.apiserver.web.dto.ContainerDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/containers")
@RestController
public class ContainerController {

    private final ContainerService service;

    @GetMapping
    public List<ContainerDto> runningContainers() {
        return service.findRunningContainers().stream()
                .map(ContainerDto::from)
                .toList();
    }

    @GetMapping("/{containerId}/status")
    public ContainerDto status(@PathVariable String containerId) {
        Container container = service.getStatus(containerId);
        return ContainerDto.from(container);
    }

    @PostMapping("/{containerId}/stop")
    public ContainerDto stop(@PathVariable String containerId) {
        Container container = service.stop(containerId);
        return ContainerDto.from(container);
    }

    @PostMapping("/{containerId}/restart")
    public ContainerDto restart(@PathVariable String containerId) {
        Container container = service.restart(containerId);
        return ContainerDto.from(container);
    }

    @PostMapping("/{containerId}/remove")
    public ContainerDto remove(@PathVariable String containerId) {
        Container container = service.remove(containerId);
        return ContainerDto.from(container);
    }
}
