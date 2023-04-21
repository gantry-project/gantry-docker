package org.gantry.apiserver.web;

import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.domain.DockerNetwork;
import org.gantry.apiserver.service.NetworkService;
import org.gantry.apiserver.web.dto.NetworkResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/networks")
public class NetworkController {
    private final NetworkService service;

    public List<NetworkResponse> list() {
        return service.findAll()
                .stream()
                .map(NetworkResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("{networkId}")
    public NetworkResponse get(@PathVariable String networkId) {
        return NetworkResponse.from(service.findById(networkId).orElseThrow());
    }

    @GetMapping("{networkName}")
    public String create(@PathVariable String networkName) {
        return service.create(networkName);
    }


}
