package com.msa.auth.controller;

import com.msa.common.domain.ResponseData;
import com.msa.common.testenv.AuthenticationTestEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import javax.naming.NamingException;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
@Slf4j
public class HelloControllerTest extends AuthenticationTestEnvironment {
    @MockBean
    private HelloController helloController;

    @Before
    public void setup() throws NamingException {
        this.setupMockMvc();
    }

    @Test
    public void getHelloWithoutAuth() throws Exception {
        ResponseData rspData = ResponseData.builder().build();
        ResponseEntity<ResponseData> rspEntity = new ResponseEntity<ResponseData>(rspData, HttpStatus.OK);

        given(helloController.hello(null)).willReturn(rspEntity);
        MvcResult result = mvc.perform(get("/api/hello")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized())
            .andReturn();
        log.info("Request : {} {}", result.getRequest().getMethod(), result.getRequest().getRequestURL().toString());
        log.info("Response : {} {} {}", result.getResponse().getStatus(), result.getResponse().getCookie("JSESSIONID"),
                result.getResponse().getHeader("X-CSRF-TOKEN"));
    }

    @Test
    public void getHelloWithAuth() throws Exception {
        ResponseData rspData = ResponseData.builder().build();
        ResponseEntity<ResponseData> rspEntity = new ResponseEntity<ResponseData>(rspData, HttpStatus.OK);

        UsernamePasswordAuthenticationToken principal =
                this.getPrincipal("test");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                new MockSecurityContext(principal));

        given(helloController.hello(null)).willReturn(rspEntity);
        MvcResult result = mvc.perform(get("/api/hello")
                .session(session)
                //.with(user("test").password("test1234"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        log.info("Request : {} {}", result.getRequest().getMethod(), result.getRequest().getRequestURL().toString());
        log.info("Response : {} {} {}", result.getResponse().getStatus(), result.getResponse().getCookie("JSESSIONID"),
                result.getResponse().getHeader("X-CSRF-TOKEN"));
    }

    public static class MockSecurityContext implements SecurityContext {

        private static final long serialVersionUID = -1386535243513362694L;

        private Authentication authentication;

        public MockSecurityContext(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public Authentication getAuthentication() {
            return this.authentication;
        }

        @Override
        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }
    }
}
