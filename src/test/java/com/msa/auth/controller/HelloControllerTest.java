package com.msa.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.auth.domain.ResponseData;
import com.msa.auth.domain.types.ResultString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class HelloControllerTest {
    @Autowired
    private MockMvc         mockMvc;

    protected ObjectMapper  mapper = new ObjectMapper();

    @Test
    public void getHelloWithNoAuth() throws Exception {
        this.mockMvc.perform(get("/api/hello")).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getHelloWithAuth() throws Exception {
        ResponseData rspData = ResponseData.builder()
                .code(200)
                .result(ResultString.SUCCESS)
                .message("test")
                .build();

        String jsonData = mapper.writeValueAsString(rspData);

        this.mockMvc.perform(get("/api/hello")
                .with(user("test").password("test1234")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonData));
    }
}
