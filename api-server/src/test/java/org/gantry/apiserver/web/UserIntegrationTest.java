package org.gantry.apiserver.web;

import org.gantry.apiserver.config.security.JWTProperties;
import org.gantry.apiserver.config.security.JWTUtil;
import org.gantry.apiserver.domain.Authority;
import org.gantry.apiserver.domain.User;
import org.gantry.apiserver.persistence.UserRepository;
import org.gantry.apiserver.web.dto.JoinRequest;
import org.gantry.apiserver.web.dto.ResetPasswordRequest;
import org.gantry.apiserver.web.dto.UserResponse;
import org.gantry.apiserver.web.dto.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
class UserIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository repository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    void before() {
        repository.deleteAll();
    }

    private <T> HttpEntity<T> createAuthzHeader(User user, T body) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(JWTProperties.AUTHZ_HEADER, JWTProperties.BEARER_PREFIX + jwtUtil.create(user));
        return new HttpEntity<>(body, headers);
    }

    private <T> ResponseEntity<T> userAPI(HttpMethod method, String url, User user, Object body, Class<T> responseType) {
        HttpEntity<?> requestEntity = createAuthzHeader(user, body);
        return restTemplate.exchange(url.formatted(user.getId()), method, requestEntity, responseType);
    }
    private <T> ResponseEntity<T> userAPI(HttpMethod method, String url, User user, Object body, ParameterizedTypeReference<T> responseType) {
        HttpEntity<?> requestEntity = createAuthzHeader(user, body);
        return restTemplate.exchange(url.formatted(user.getId()), method, requestEntity, responseType);
    }

    private UserResponse createUser(String username) {
        HttpEntity<JoinRequest> requestEntity = new HttpEntity<>(getJoinRequestWith(username));
        return restTemplate.exchange("/api/v1/users", POST, requestEntity, UserResponse.class).getBody();
    }

    private JoinRequest getJoinRequestWith(String username) {
        return JoinRequest.builder()
                .username(username)
                .email(username + "@user.com")
                .password(username + "_password")
                .confirmPassword(username + "_password")
                .build();
    }


    private void assertUser(UserResponse userResponse, UserUpdateRequest updateUser) {
        assertThat(updateUser.getId()).isEqualTo(userResponse.getId());
        assertThat(updateUser.getUsername()).isEqualTo(userResponse.getUsername());
        assertThat(updateUser.getEmail()).isEqualTo(userResponse.getEmail());
    }

    private void assertUser(UserResponse userResponse, User user) {
        assertThat(user.getId()).isEqualTo(userResponse.getId());
        assertThat(user.getUsername()).isEqualTo(userResponse.getUsername());
        assertThat(user.getEmail()).isEqualTo(userResponse.getEmail());
        assertThat(user.getAuthority()).isEqualTo(userResponse.getAuthority());
        assertThat(user.isEnabled()).isEqualTo(userResponse.isEnabled());
    }

    @Test
    void join() {
        // when
        HttpEntity<JoinRequest> requestEntity = new HttpEntity<>(getJoinRequestWith("user01"));
        ResponseEntity<UserResponse> response = restTemplate.exchange("/api/v1/users", POST, requestEntity, UserResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserResponse responseUser = response.getBody();
        assertThat(responseUser.getId()).isNotNull();
        assertThat(responseUser.getUsername()).isEqualTo(responseUser.getUsername());
        assertThat(responseUser.getEmail()).isEqualTo(responseUser.getEmail());
        assertThat(responseUser.getAuthority()).isEqualTo(responseUser.getAuthority());

        Optional<User> userFromRepo = repository.findById(responseUser.getId());
        assertThat(userFromRepo).isPresent();

        assertUser(responseUser, userFromRepo.get());
    }

    @Test
    void getUser() {
        // given
        UserResponse user = createUser("user01");

        // when
        ResponseEntity<UserResponse> response = userAPI(GET, "/api/v1/users/%d", user.toUser(), null, UserResponse.class);
//        ResponseEntity<UserResponse> response = restTemplate.getForEntity("/api/v1/users/%d".formatted(user.getId()), UserResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(user);
    }

    @Test
    void list() {
        // given
        UserResponse user01 = createUser("user01");
        UserResponse user02 = createUser("user02");

        // when
        ResponseEntity<List<UserResponse>> response = restTemplate.exchange("/api/v1/users", GET, null, new ParameterizedTypeReference<List<UserResponse>>(){});

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(List.of(user01, user02));
    }

    @Test
    void update() {
        // given
        User user = createUser("user01").toUser();

        // when
        UserUpdateRequest updateUser = UserUpdateRequest.from(user);
        updateUser.setEmail("modified_email");
        updateUser.setUsername("modified_username");
        HttpEntity<UserUpdateRequest> requestEntity = new HttpEntity<>(updateUser);
        ResponseEntity<UserResponse> response = restTemplate.exchange("/api/v1/users/%d".formatted(updateUser.getId()), PUT, requestEntity, UserResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserResponse responseUser = response.getBody();
        assertUser(responseUser, updateUser);

        Optional<User> userFromRepo = repository.findById(responseUser.getId());
        assertThat(userFromRepo).isPresent();
        assertUser(responseUser, userFromRepo.get());
    }

    @Test
    void delete() {
        // given
        User user = createUser("user01").toUser();

        // when
        ResponseEntity<UserResponse> response = restTemplate.exchange("/api/v1/users/%d".formatted(user.getId()), DELETE, null, UserResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserResponse responseUser = response.getBody();
        assertUser(responseUser, user);

        Optional<User> userFromRepo = repository.findById(responseUser.getId());
        assertThat(userFromRepo).isEmpty();
    }

    @Test
    void updateAuthority() {
        // given
        User user = createUser("user01").toUser();
        assertThat(user.getAuthority()).isEqualTo(Authority.USER);

        // when
        UserUpdateRequest updateUser = UserUpdateRequest.from(user);

        updateUser.setAuthority(Authority.ADMIN);
        HttpEntity<UserUpdateRequest> requestEntity = new HttpEntity<>(updateUser);
        ResponseEntity<UserResponse> response = restTemplate.exchange("/api/v1/users/%d".formatted(updateUser.getId()), PUT, requestEntity, UserResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserResponse responseUser = response.getBody();
        assertUser(responseUser, updateUser);

        Optional<User> userFromRepo = repository.findById(responseUser.getId());
        assertThat(userFromRepo).isPresent();

        User actualUser = userFromRepo.get();
        assertUser(responseUser, actualUser);
    }

    @Test
    void disableUser() {
        // given
        User user = createUser("user01").toUser();
        assertThat(user.isEnabled()).isTrue();

        // when
        ResponseEntity<UserResponse> response = restTemplate.exchange("/api/v1/users/%d/disabled".formatted(user.getId()), PUT, null, UserResponse.class);
//        ResponseEntity<UserResponse> response = userAPI(PUT, "/api/v1/users/%d/disabled", user, null, UserResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserResponse responseUser = response.getBody();
        user.setEnabled(false);
        assertUser(responseUser, user);

        Optional<User> userFromRepo = repository.findById(responseUser.getId());
        assertThat(userFromRepo).isPresent();

        User actualUser = userFromRepo.get();
        assertUser(responseUser, actualUser);
    }

    @Test
    void enableUser() {
        // given
        User user = createUser("user01").toUser();

        // when
        restTemplate.exchange("/api/v1/users/%d/disabled".formatted(user.getId()), PUT, null, UserResponse.class);
        User disabledUser = repository.findById(user.getId()).get();
        assertThat(disabledUser.isEnabled()).isEqualTo(false);
        ResponseEntity<UserResponse> response = restTemplate.exchange("/api/v1/users/%d/enabled".formatted(user.getId()), PUT, null, UserResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserResponse responseUser = response.getBody();
        assertUser(responseUser, user);

        Optional<User> userFromRepo = repository.findById(responseUser.getId());
        assertThat(userFromRepo).isPresent();

        User actualUser = userFromRepo.get();
        assertUser(responseUser, actualUser);
    }

    @Test
    void resetPassword() {
        // given
        User user = createUser("user01").toUser();

        // when
        ResetPasswordRequest updateUser = ResetPasswordRequest.builder()
                .id(user.getId())
                .password("new_password")
                .confirmPassword("new_password")
                .build();
        HttpEntity<ResetPasswordRequest> requestEntity = new HttpEntity<>(updateUser);
        ResponseEntity<UserResponse> response = restTemplate.exchange("/api/v1/users/%d/password".formatted(updateUser.getId()), PUT, requestEntity, UserResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserResponse responseUser = response.getBody();
        assertUser(responseUser, user);

        Optional<User> userFromRepo = repository.findById(responseUser.getId());
        assertThat(userFromRepo).isPresent();

        User actualUser = userFromRepo.get();
        assertThat(passwordEncoder.matches(updateUser.getPassword(), actualUser.getPassword())).isTrue();
    }

}