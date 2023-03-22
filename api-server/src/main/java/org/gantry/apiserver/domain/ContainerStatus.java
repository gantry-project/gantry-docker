package org.gantry.apiserver.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContainerStatus {

    CREATED("CREATED"),
    RESTARTING("RESTARTING"),
    RUNNING("RUNNING"),
    REMOVING("REMOVING"),
    PAUSED("PAUSED"),
    EXITED("EXITED"),
    DEAD("DEAD"),
    UNKNOWN("UNKNOWN");

    private final String title;

}