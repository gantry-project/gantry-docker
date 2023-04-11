package org.gantry.apiserver.web;

import org.gantry.apiserver.config.security.JWTProperties;
import org.gantry.apiserver.config.security.JWTUtil;
import org.gantry.apiserver.domain.Authority;
import org.gantry.apiserver.domain.Platform;
import org.gantry.apiserver.domain.PlatformType;
import org.gantry.apiserver.domain.User;
import org.gantry.apiserver.persistence.PlatformRepository;
import org.gantry.apiserver.web.dto.PlatformCreateRequest;
import org.gantry.apiserver.web.dto.PlatformResponse;
import org.gantry.apiserver.web.dto.PlatformUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PlatformIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PlatformRepository repository;

    @Autowired
    private JWTUtil jwtUtil;


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
        return new HttpEntity<>(body, headers);
    }



    private void assertPlatform(Platform platform, PlatformResponse platformResponse) {
        assertThat(platform.getId()).isEqualTo(platformResponse.getId());
        assertThat(platform.getUrl()).isEqualTo(platformResponse.getUrl());
        assertThat(platform.getName()).isEqualTo(platformResponse.getName());
        assertThat(platform.getDescription()).isEqualTo(platformResponse.getDescription());
        assertThat(platform.getType()).isEqualTo(platformResponse.getType());
        assertThat(platform.isActive()).isEqualTo(platformResponse.isActive());
    }

    private void assertPlatform(Platform platform, PlatformUpdateRequest updateRequest) {
        assertThat(platform.getId()).isEqualTo(updateRequest.getId());
        assertThat(platform.getUrl()).isEqualTo(updateRequest.getUrl());
        assertThat(platform.getName()).isEqualTo(updateRequest.getName());
        assertThat(platform.getDescription()).isEqualTo(updateRequest.getDescription());
        assertThat(platform.getType()).isEqualTo(updateRequest.getType());
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

        assertPlatform(data.get(), responseBody);
    }

    @Test
    void getUser() {
        // when
        PlatformResponse testPlatform = createPlatform("testPlatform");

        // then
        HttpEntity<Object> requestEntity = createHttpEntity(null);
        ResponseEntity<PlatformResponse> response = restTemplate.exchange("/api/v1/platforms/%d".formatted(testPlatform.getId()), GET, requestEntity, PlatformResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(testPlatform);
    }

    private PlatformResponse createPlatform(String platformName) {
        PlatformCreateRequest request = PlatformCreateRequest.builder()
                .type(PlatformType.DOCKER)
                .url("tcp://" + platformName + ":2375")
                .name(platformName)
                .description(platformName + " for test")
                .build();
        HttpEntity<PlatformCreateRequest> requestEntity = createHttpEntity(request);
        ResponseEntity<PlatformResponse> response = restTemplate.exchange("/api/v1/platforms", POST, requestEntity, PlatformResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    @Test
    void list() {
        // given
        PlatformResponse testPlatform01 = createPlatform("testPlatform01");
        PlatformResponse testPlatform02 = createPlatform("testPlatform02");

        // when
        HttpEntity<PlatformCreateRequest> requestEntity = createHttpEntity(null);
        ResponseEntity<List<PlatformResponse>> response = restTemplate.exchange("/api/v1/platforms", GET, requestEntity, new ParameterizedTypeReference<List<PlatformResponse>>(){});

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(List.of(testPlatform01, testPlatform02));
    }

    @Test
    void updatePlatform() {
        // given
        Platform testPlatform = createPlatform("testPlatform").toPlatform();

        // when
        PlatformUpdateRequest updatePlatform = PlatformUpdateRequest.from(testPlatform);
        updatePlatform.setDescription("modified_description");
        updatePlatform.setName("modified_name");
        updatePlatform.setUrl("tcp://modified_url:2375");
        updatePlatform.setType(PlatformType.DOCKER_COMPOSE);
        HttpEntity<PlatformUpdateRequest> requestEntity = createHttpEntity(updatePlatform);
        ResponseEntity<PlatformResponse> response = restTemplate.exchange("/api/v1/platforms/%d".formatted(testPlatform.getId()), PUT, requestEntity, PlatformResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        PlatformResponse responsePlatform = response.getBody();
        assertPlatform(responsePlatform.toPlatform(), updatePlatform);

        Optional<Platform> platformFromRepo = repository.findById(responsePlatform.getId());
        assertThat(platformFromRepo).isPresent();
        assertPlatform(platformFromRepo.get(), responsePlatform);
    }

    @Test
    void deletePlatform() {
        // given
        Platform testPlatform = createPlatform("testPlatform").toPlatform();

        // when
        HttpEntity<PlatformUpdateRequest> requestEntity = createHttpEntity(null);
        ResponseEntity<PlatformResponse> response = restTemplate.exchange("/api/v1/platforms/%d".formatted(testPlatform.getId()), DELETE, requestEntity, PlatformResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        PlatformResponse responsePlatform = response.getBody();
        assertPlatform(testPlatform, responsePlatform);

        Optional<Platform> platformFromRepo = repository.findById(responsePlatform.getId());
        assertThat(platformFromRepo).isEmpty();
    }

    @Test
    void activatePlatform() {
        // given

        // given
        Platform testPlatform01 = createPlatform("testPlatform01").toPlatform();
        Platform testPlatform02 = createPlatform("testPlatform02").toPlatform();
        Platform testPlatform03 = createPlatform("testPlatform03").toPlatform();

        // when
        HttpEntity<PlatformUpdateRequest> requestEntity = createHttpEntity(null);
        ResponseEntity<PlatformResponse> response = restTemplate.exchange("/api/v1/platforms/%d/activate".formatted(testPlatform01.getId()), PUT, requestEntity, PlatformResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PlatformResponse responsePlatform = response.getBody();
        testPlatform01.setActive(true);
        assertPlatform(testPlatform01, responsePlatform);

        List<Platform> activePlatforms = repository.findByActive(true);
        assertThat(activePlatforms.size()).isEqualTo(1);
        assertPlatform(activePlatforms.get(0), responsePlatform);
    }
}