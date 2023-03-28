package org.gantry.apiserver.web;

import org.gantry.apiserver.domain.Application;
import org.gantry.apiserver.domain.Container;
import org.gantry.apiserver.service.ContainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.gantry.apiserver.domain.ContainerStatus.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ContainerController.class)
class ContainerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContainerService service;

    private Application testApplication;
    private Container testContainer;

    @BeforeEach
    void createFixture() {
        testContainer = Container.builder()
                .id("testid0001")
                .status(RUNNING)
                .build();
        testApplication = Application.builder()
                .image("docker/getting-started")
                .title("hello_docker")
                .container(testContainer)
                .build();

        testContainer.setApplication(testApplication);
    }

    @Test
    void stop() throws Exception {
        testContainer.setStatus(PAUSED);
        given(service.stop(anyString())).willReturn(testContainer);

        mockMvc.perform(post("/containers/testid0001/stop"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("testid0001"))
                .andExpect(jsonPath("status").value("PAUSED"));
    }


    @Test
    void restart() throws Exception {
        testContainer.setStatus(RESTARTING);
        given(service.restart(anyString())).willReturn(testContainer);

        mockMvc.perform(post("/containers/testid0001/restart"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("testid0001"))
                .andExpect(jsonPath("status").value("RESTARTING"));
    }

    @Test
    void remove() throws Exception {
        testContainer.setStatus(REMOVING);
        given(service.remove(anyString())).willReturn(testContainer);

        mockMvc.perform(post("/containers/testid0001/remove"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("testid0001"))
                .andExpect(jsonPath("status").value("REMOVING"));
    }

    @Test
    void getStatus() throws Exception {
        given(service.getStatus(anyString())).willReturn(testContainer);

        mockMvc.perform(get("/containers/testid0001/status"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("testid0001"))
                .andExpect(jsonPath("status").value("RUNNING"));
    }

    @Test
    void list() throws Exception {
        given(service.findRunningContainers()).willReturn(List.of(testContainer, testContainer));

        mockMvc.perform(get("/containers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

}