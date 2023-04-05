package org.gantry.apiserver.web;

import org.gantry.apiserver.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @BeforeEach
    void createFixture() {
    }

    @WithMockUser
    @Test
    void join() throws Exception {

//        mockMvc.perform(post("/auth/users").with(csrf()))
//                .andDo(print())
//                .andExpect(status().isOk());

    }

    @WithMockUser
    @Test
    void getUser() throws Exception {
    }

    @WithMockUser
    @Test
    void update() throws Exception {
    }

    @WithMockUser
    @Test
    void delete() throws Exception {
    }

    @WithMockUser
    @Test
    void updateAuthority() throws Exception {
    }

    @WithMockUser
    @Test
    void updatePassword() throws Exception {
    }

}