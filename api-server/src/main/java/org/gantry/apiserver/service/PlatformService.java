package org.gantry.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.gantry.apiserver.domain.Platform;
import org.gantry.apiserver.exception.NoSuchPlatformException;
import org.gantry.apiserver.persistence.PlatformRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlatformService {
    private final PlatformRepository repository;

    public List<Platform> findAll() {
        return repository.findAll();
    }

    public Platform findById(Long platformId) {
        return repository.findById(platformId)
                .orElseThrow(NoSuchPlatformException.with(platformId));
    }

    @Transactional
    public Platform createPlatform(Platform platform) {
        return repository.save(platform);
    }

    @Transactional
    public Platform updatePlatform(Platform platform) {
        throwIfPlatformNotExist(platform.getId());
        return repository.save(platform);
    }

    private void throwIfPlatformNotExist(long platformId) {
        if (!repository.existsById(platformId)) {
            throw new NoSuchPlatformException(platformId);
        }
    }

    @Transactional
    public Platform deletePlatform(Long platformId) {
        Platform platform = findById(platformId);
        repository.delete(platform);
        return platform;
    }

    @Transactional
    public Platform activatePlatform(Long platformId) {
        Platform platform = findById(platformId);
        repository.findByActive(true).stream()
                .forEach(p -> p.setActive(false));
        platform.setActive(true);
        return repository.save(platform);
    }
}
