package org.gantry.apiserver.web;

import org.gantry.apiserver.domain.ContainerInfo;
import org.gantry.apiserver.domain.ContainerStatus;
import org.gantry.apiserver.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
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
    private ApplicationService service;

    @Test
    void stop() throws Exception {
        var dto = ContainerInfo.builder().id(9999L).applicationId(1L).status(ContainerStatus.PAUSED).build();
        given(service.stop(anyLong())).willReturn(dto);

        mockMvc.perform(post("/containers/9999/stop"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("9999"))
                .andExpect(jsonPath("status").value("PAUSED"));
    }

    @Test
    void remove() throws Exception {
        var dto = ContainerInfo.builder().id(9999L).applicationId(1L).status(ContainerStatus.REMOVING).build();
        given(service.remove(anyLong())).willReturn(dto);

        mockMvc.perform(post("/containers/9999/remove"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("9999"))
                .andExpect(jsonPath("status").value("REMOVING"));
    }

    @Test
    void getStatus() throws Exception {
        var dto = ContainerInfo.builder().id(9999L).applicationId(1L).status(ContainerStatus.RUNNING).build();
        given(service.getStatus(anyLong())).willReturn(dto);

        mockMvc.perform(get("/containers/9999/status"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("9999"))
                .andExpect(jsonPath("status").value("RUNNING"));
    }

    @Test
    void restart() throws Exception {
        ContainerInfo dto = ContainerInfo.builder().id(1L).applicationId(99L).status(ContainerStatus.RESTARTING).build();
        given(service.restart(anyLong())).willReturn(dto);

        mockMvc.perform(post("/containers/1/restart"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("status").value("RESTARTING"));
    }

}