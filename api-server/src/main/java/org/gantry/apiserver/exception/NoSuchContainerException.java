package org.gantry.apiserver.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public class NoSuchContainerException extends RuntimeException {
    private final String id;

    public static Supplier<NoSuchContainerException> with(String containerId) {
        return () -> new NoSuchContainerException(containerId);
    }
}
