package org.gantry.apiserver.config.security;

import org.gantry.apiserver.web.dto.UserTokenResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SecurityConfigurationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void test_create_token() throws JSONException {
        HttpHeaders headers = new HttpHeaders() {{
            setContentType(MediaType.APPLICATION_JSON);
        }};
        String body = new JSONObject() {{
            put("user", "user");
            put("password", "user");
        }}.toString();
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<UserTokenResponse> response = restTemplate.postForEntity("/auth/token", request, UserTokenResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}