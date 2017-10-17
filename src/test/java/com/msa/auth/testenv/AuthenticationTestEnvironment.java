package com.msa.auth.testenv;

import com.msa.common.service.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.naming.NamingException;

//@RunWith(SpringRunner.class)
@WebAppConfiguration
@Slf4j
public class AuthenticationTestEnvironment {
    @Resource
    private FilterChainProxy        springSecurityFilterChain;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected UserDetailsService    userDetailsService;

    protected MockMvc               mvc;

    protected UsernamePasswordAuthenticationToken getPrincipal(String username) {
        UserDetails user = this.userDetailsService.loadUserByUsername(username);
        log.info("Authentication user : {}/{}", username, user.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        return authenticationToken;
    }

    //@Before
    protected void setupMockMvc() throws NamingException {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .addFilter(this.springSecurityFilterChain)
                .build();
        log.info("Mocmvc built");
    }
}
