package org.gantry.apiserver.config.security;

import org.gantry.apiserver.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@Import(JWTProperties.class)
@ExtendWith(SpringExtension.class)
public class JWTUtilTest {
    @Autowired
    private JWTProperties jwtProperties;

    @Test
    void test_create_token() {
        User user = User.builder().username("user001").build();

        JWTUtil jwt = new JWTUtil(jwtProperties);
        String token = jwt.create(user);
        User decoded = jwt.decode(token);

        assertThat(decoded.getUsername()).isEqualTo("user001");
    }

    @Test
    void test_verity() throws InterruptedException {
        User user = User.builder().username("user001").build();

        jwtProperties.setExpirationSecond(2);
        JWTUtil jwt = new JWTUtil(jwtProperties);
        String token = jwt.create(user);
        boolean isValidBefore = jwt.verify(token);

        Thread.sleep(2000);
        boolean isValidAfter = jwt.verify(token);

        assertThat(isValidBefore).isTrue();
        assertThat(isValidAfter).isFalse();
    }

    @Test
    void test_verity_with_blank_string() {
        JWTUtil jwt = new JWTUtil(jwtProperties);
        boolean withEmptyString = jwt.verify("");
        boolean withWhitespace = jwt.verify(" ");
        boolean withNull = jwt.verify(null);

        assertThat(withEmptyString).isFalse();
        assertThat(withWhitespace).isFalse();
        assertThat(withNull).isFalse();
    }
}
