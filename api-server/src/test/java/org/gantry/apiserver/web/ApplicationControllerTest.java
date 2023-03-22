package org.gantry.apiserver.web;

import org.gantry.apiserver.domain.Application;
import org.gantry.apiserver.domain.ContainerInfo;
import org.gantry.apiserver.domain.ContainerStatus;
import org.gantry.apiserver.service.ApplicationService;
import org.gantry.apiserver.web.dto.ApplicationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
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

    @Test
    void list() throws Exception {
        given(service.findAll()).willReturn(List.of(Application.builder().title("test").image("repo/repo:latest").build()));

        mockMvc.perform(get("/applications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("[0].title").value("test"));
    }

    @Test
    void getOne() throws Exception {
        var dto = Application.builder().id(1L).title("test").image("repo/repo:latest").build();
        given(service.findById(anyLong())).willReturn(Optional.of(dto));

        mockMvc.perform(get("/applications/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("title").value("test"));
    }

    @Test
    void execute() throws Exception {
        var dto = ContainerInfo.builder().id("1234").applicationId(1L).status(ContainerStatus.CREATED).build();
        given(service.execute(anyLong())).willReturn(dto);

        mockMvc.perform(post("/applications/1/execute"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("9999"));
    }
}