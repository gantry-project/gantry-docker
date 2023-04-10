package org.gantry.apiserver.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@Getter
public class NoSuchPlatformException extends RuntimeException {

    public NoSuchPlatformException(long id) {
        this("%s is not found".formatted(id));
    }

    public NoSuchPlatformException(String message) {
        super(message);
    }

    public static Supplier<NoSuchPlatformException> with(long platformId) {
        return () -> new NoSuchPlatformException(platformId);
    }
}
