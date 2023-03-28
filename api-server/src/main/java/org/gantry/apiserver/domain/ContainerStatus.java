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

    public static ContainerStatus of(String status){
        for(ContainerStatus now : ContainerStatus.values()){
            if(now.getTitle().equals(status))return now;
        }
        throw new IllegalArgumentException();
    }

}