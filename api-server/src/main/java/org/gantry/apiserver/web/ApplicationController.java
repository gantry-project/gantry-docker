package org.gantry.apiserver.web;

import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.web.dto.ApplicationDto;
import org.gantry.apiserver.service.ApplicationService;
import org.gantry.apiserver.web.dto.ContainerDto;
import org.gantry.apiserver.exception.NoSuchApplicationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/applications")
@RestController
public class ApplicationController {

    private final ApplicationService service;
    @GetMapping()
    public List<ApplicationDto> list() {
        return service.findAll().stream()
                .map(ApplicationDto::from)
                .toList();
    }

    @GetMapping("/{applicationId}")
    public ApplicationDto get(@PathVariable Long applicationId) {
        return service.findById(applicationId)
                .map(ApplicationDto::from)
                .orElseThrow(() -> new NoSuchApplicationException());
    }

    @PostMapping("/{applicationId}/execute")
    public ContainerDto execute(@PathVariable Long applicationId) {
        return ContainerDto.from(service.execute(applicationId));
    }
}
