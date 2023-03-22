package org.gantry.apiserver.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlatformType {

    DOCKER("DOCKER", "docker"),
    DOCKER_COMPOSE("DOCKER_COMPOSE", "docker compose");

    private final String key;
    private final String title;

}