package org.gantry.apiserver.exception;

import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

public class UserNotFoundException extends RuntimeException {
    private final Long id;
    private final String username;
    private final String message;

    public UserNotFoundException(Long id, String username, String message) {
        super(message);
        this.id = id;
        this.username = username;
        this.message = message;
    }
    public UserNotFoundException(Long id) {
        this(id, "User id %d is not found.".formatted(id));
    }

    public UserNotFoundException(Long id, String message) {
        super(message);
        this.id = id;
        this.username = null;
        this.message = message;
    }

    public UserNotFoundException(String username) {
        this(username, "Username %s is not found.".formatted(username));
    }

    public UserNotFoundException(String username, String message) {
        super(message);
        this.id = null;
        this.username = username;
        this.message = message;
    }

    public static Supplier<UserNotFoundException> with(Long id) {
        return () -> new UserNotFoundException(id);
    }

    public static Supplier<UserNotFoundException> with(String username) {
        return () -> new UserNotFoundException(username);
    }

    private String getUsernameOrId() {
        return username == null ? id.toString() : username;
    }
}
