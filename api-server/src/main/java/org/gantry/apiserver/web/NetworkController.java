package org.gantry.apiserver.web;

import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.service.NetworkService;
import org.gantry.apiserver.web.dto.NetworkResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/networks")
public class NetworkController {
    private final NetworkService service;
    @GetMapping()
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

    @PostMapping("{networkName}/create")
    public String create(@PathVariable String networkName) {
        return service.create(networkName);
    }

    @PostMapping("{networkId}/run")
    public List<String> run(@PathVariable String networkId) {
        return service.run(networkId);
    }
    @PostMapping("{networkId}/stop")
    public void stop(@PathVariable String networkId){
        service.stop(networkId);
    }

    @PostMapping("{networkId}/remove")
    public void remove(@PathVariable String networkId){
        service.remove(networkId);
    }
}
