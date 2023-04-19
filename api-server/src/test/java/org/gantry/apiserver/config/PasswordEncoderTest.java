package org.gantry.apiserver.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;

public class PasswordEncoderTest {
    @Test
    void passwordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode("gantry");
        System.out.println(password);
        String expectedPassword = "$2a$10$27su2oYwVjfqhMax7Tn.MuKEgPyKEfbjjkD8/ovGohGwl21PVlX3O";
        assertThat(encoder.matches("gantry", expectedPassword)).isTrue();
    }
}
