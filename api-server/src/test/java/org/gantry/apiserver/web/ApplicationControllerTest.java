package org.gantry.apiserver.web;

import org.gantry.apiserver.domain.Application;
import org.gantry.apiserver.domain.Container;
import org.gantry.apiserver.service.ApplicationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.gantry.apiserver.domain.ContainerStatus.RUNNING;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ApplicationController.class)
class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationService service;

    private static Container testContainer;
    private static Application testApplication;

    @BeforeAll
    static void createFixtures() {
        testContainer = Container.builder().id("test0001").status(RUNNING).build();
        testApplication = Application.builder().title("test").image("repo/repo:latest").container(testContainer).build();
        testContainer.setApplication(testApplication);
    }

    @WithMockUser
    @Test
    void list() throws Exception {
        given(service.findAll()).willReturn(List.of(testApplication));

        mockMvc.perform(get("/api/v1/applications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("[0].title").value("test"));
    }

    @WithMockUser
    @Test
    void getOne() throws Exception {
        given(service.findById(anyLong())).willReturn(Optional.of(testApplication));

        mockMvc.perform(get("/api/v1/applications/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("title").value("test"));
    }

    @WithMockUser
    @Test
    void execute() throws Exception {
        given(service.execute(anyLong())).willReturn(testContainer);

        mockMvc.perform(post("/api/v1/applications/1/execute").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("test0001"));
    }
}