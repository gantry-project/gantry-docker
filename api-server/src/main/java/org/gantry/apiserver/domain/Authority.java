package org.gantry.apiserver.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;

public enum Authority implements GrantedAuthority {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String authority;

    Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public String toString() {
        return this.authority;
    }

    public static boolean isValid(String authority) {
        return Arrays.stream(Authority.values()).anyMatch(a -> a.name().equals(authority));
    }
}
