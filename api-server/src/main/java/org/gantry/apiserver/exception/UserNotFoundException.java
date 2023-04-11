package org.gantry.apiserver.exception;

import java.util.function.Supplier;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public static Supplier<UserNotFoundException> with(Long id) {
        return () -> new UserNotFoundException("%d is not found.".formatted(id));
    }

    public static Supplier<UserNotFoundException> withUsername(String username) {
        return () -> new UserNotFoundException("%s is not found.".formatted(username));
    }
}
