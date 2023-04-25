package org.gantry.apiserver.service;

import com.github.dockerjava.api.DockerClient;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.domain.Container;
import org.gantry.apiserver.domain.DockerNetwork;
import org.gantry.apiserver.persistence.NetworkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NetworkService {
    private final NetworkRepository networkRepository;
    private final DockerClient dockerClient;

    @Transactional
    public String create(String networkName) {

        DockerNetwork network = DockerNetwork.builder()
                .name(networkName)
                .build();
        network.create(dockerClient);
        networkRepository.save(network);
        return network.getId();
    }

    @Transactional
    public void addContainer(String networkId, List<Container> containers) {
        DockerNetwork network = networkRepository.findById(networkId).orElseThrow(NotFoundException::new);
        network.getContainers().addAll(containers);
    }

    public List<String> run(String networkId) {
        DockerNetwork network = networkRepository.findById(networkId).orElseThrow(NotFoundException::new);
        if (network.getContainers().size() == 0) throw new RuntimeException("network has not any container");
        List<String> networkContainers = network.run(dockerClient);
        return networkContainers;
    }

    public void stop(String networkId) {
        DockerNetwork network = networkRepository.findById(networkId).orElseThrow(NotFoundException::new);
        network.stop(dockerClient);
    }

    public void remove(String networkId) {
        DockerNetwork network = networkRepository.findById(networkId).orElseThrow(NotFoundException::new);
        network.remove(dockerClient);
        networkRepository.deleteById(network.getId());
    }

    public Optional<DockerNetwork> findById(String networkId){
        return networkRepository.findById(networkId);
    }

    public List<DockerNetwork> findAll(){
        return networkRepository.findAll();
    }
}
