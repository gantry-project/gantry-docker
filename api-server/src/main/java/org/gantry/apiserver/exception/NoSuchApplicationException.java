package org.gantry.apiserver.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public class NoSuchApplicationException extends RuntimeException {
    private final long id;

    public static Supplier<NoSuchApplicationException> with(long applicationId) {
        return () -> new NoSuchApplicationException(applicationId);
    }
}
