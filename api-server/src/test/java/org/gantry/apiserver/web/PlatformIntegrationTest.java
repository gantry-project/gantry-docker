package org.gantry.apiserver.web;

import org.gantry.apiserver.config.security.JWTProperties;
import org.gantry.apiserver.config.security.JWTUtil;
import org.gantry.apiserver.domain.Authority;
import org.gantry.apiserver.domain.Platform;
import org.gantry.apiserver.domain.PlatformType;
import org.gantry.apiserver.domain.User;
import org.gantry.apiserver.persistence.PlatformRepository;
import org.gantry.apiserver.web.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PlatformIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PlatformRepository repository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    void before() {
        repository.deleteAll();
    }

    private <T> HttpEntity<T> createHttpEntity(T body) {
        User admin = User.builder()
                .id(9L)
                .username("admin_user")
                .email("admin_user@email.com")
                .enabled(true)
                .authority(Authority.ADMIN)
                .build();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(JWTProperties.AUTHZ_HEADER, JWTProperties.BEARER_PREFIX + jwtUtil.create(admin));
        return new HttpEntity<T>(body, headers);
    }




    @Test
    void create() {
        // when
        PlatformCreateRequest request = PlatformCreateRequest.builder()
                .type(PlatformType.DOCKER)
                .url("http://testserver:2375")
                .name("new docker")
                .description("new docker for test")
                .build();
        HttpEntity<PlatformCreateRequest> requestEntity = createHttpEntity(request);
        ResponseEntity<PlatformResponse> response = restTemplate.exchange("/api/v1/platforms", POST, requestEntity, PlatformResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        PlatformResponse responseBody = response.getBody();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(request.getName()).isEqualTo(responseBody.getName());
        assertThat(request.getDescription()).isEqualTo(responseBody.getDescription());
        assertThat(request.getUrl()).isEqualTo(responseBody.getUrl());
        assertThat(request.getType()).isEqualTo(responseBody.getType());

        Optional<Platform> data = repository.findById(responseBody.getId());
        assertThat(data).isPresent();

//        assertEqualsPlatform(responseBody, data.get());
    }

//    @Test
//    void getUser() {
//        // given
//        UserResponse user = createUser("user01");
//
//        // when
//        ResponseEntity<UserResponse> response = userAPI(GET, "/api/v1/users/%d", user.toUser(), null, UserResponse.class);
////        ResponseEntity<UserResponse> response = restTemplate.getForEntity("/api/v1/users/%d".formatted(user.getId()), UserResponse.class);
//
//        // then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isEqualTo(user);
//    }
//
//    @Test
//    void list() {
//        // given
//        UserResponse user01 = createUser("user01");
//        UserResponse user02 = createUser("user02");
//
//        // when
//        ResponseEntity<List<UserResponse>> response = restTemplate.exchange("/api/v1/users", GET, null, new ParameterizedTypeReference<List<UserResponse>>(){});
//
//        // then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isEqualTo(List.of(user01, user02));
//    }
//
//    @Test
//    void updatePlatform() {
//        // given
//        User user = createUser("user01").toUser();
//
//        // when
//        UserUpdateRequest updateUser = UserUpdateRequest.from(user);
//        updateUser.setEmail("modified_email");
//        updateUser.setUsername("modified_username");
//        HttpEntity<UserUpdateRequest> requestEntity = new HttpEntity<>(updateUser);
//        ResponseEntity<UserResponse> response = restTemplate.exchange("/api/v1/users/%d".formatted(updateUser.getId()), PUT, requestEntity, UserResponse.class);
//
//        // then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        UserResponse responseUser = response.getBody();
//        assertUser(responseUser, updateUser);
//
//        Optional<User> userFromRepo = repository.findById(responseUser.getId());
//        assertThat(userFromRepo).isPresent();
//        assertUser(responseUser, userFromRepo.get());
//    }
//
//    @Test
//    void deletePlatform() {
//        // given
//        User user = createUser("user01").toUser();
//
//        // when
//        ResponseEntity<UserResponse> response = restTemplate.exchange("/api/v1/users/%d".formatted(user.getId()), DELETE, null, UserResponse.class);
//
//        // then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        UserResponse responseUser = response.getBody();
//        assertUser(responseUser, user);
//
//        Optional<User> userFromRepo = repository.findById(responseUser.getId());
//        assertThat(userFromRepo).isEmpty();
//    }
//
//    @Test
//    void activePlatform() {
//        // given
//        User user = createUser("user01").toUser();
//
//        // when
//        restTemplate.exchange("/api/v1/users/%d/disabled".formatted(user.getId()), PUT, null, UserResponse.class);
//        User disabledUser = repository.findById(user.getId()).get();
//        assertThat(disabledUser.isEnabled()).isEqualTo(false);
//        ResponseEntity<UserResponse> response = restTemplate.exchange("/api/v1/users/%d/enabled".formatted(user.getId()), PUT, null, UserResponse.class);
//
//        // then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        UserResponse responseUser = response.getBody();
//        assertUser(responseUser, user);
//
//        Optional<User> userFromRepo = repository.findById(responseUser.getId());
//        assertThat(userFromRepo).isPresent();
//
//        User actualUser = userFromRepo.get();
//        assertUser(responseUser, actualUser);
//    }

}