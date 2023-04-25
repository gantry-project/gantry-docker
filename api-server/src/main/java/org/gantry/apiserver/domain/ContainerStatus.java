package org.gantry.apiserver.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

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
    UNKNOWN("UNKNOWN"),
    NOTFOUND("NOTFOUND");

    private final String title;

    public static ContainerStatus of(String status){
        return Arrays.stream(ContainerStatus.values())
                .filter(s -> s.getTitle().equals(status.toUpperCase()))
                .findFirst()
                .orElse(UNKNOWN);
    }

}