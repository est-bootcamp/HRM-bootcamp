package com.example.est_bootcamp.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginFlowTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginSuccess() throws Exception {
        mockMvc.perform(post("/doLogin")
                        .param("loginId", "testuser1")  // DB에 있는 login_id
                        .param("password", "test1234"))  // DB 해시와 매칭되는 평문 비밀번호
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"));
    }
}