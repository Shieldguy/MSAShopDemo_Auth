package com.msa.auth.config.security;

import com.msa.auth.config.security.filter.CsrfTokenBindingRspHeaderFilter;
import com.msa.auth.config.security.handler.LoginFailureHandler;
import com.msa.auth.config.security.handler.SignoutSuccessHandler;
import com.msa.auth.config.security.handler.LoginSuccessHandler;
import com.msa.auth.service.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@Slf4j
public class WebSecurity {
    @EnableWebSecurity
    public class WebServiceSecurity extends WebSecurityConfigurerAdapter {
        @Autowired
        private AuthEntryPoint          authEntryPoint;
        @Autowired
        private LoginSuccessHandler     loginSuccessHandler;
        @Autowired
        private LoginFailureHandler     loginFailureHandler;
        @Autowired
        private SignoutSuccessHandler   signoutSuccessHandler;

        @Autowired
        private UserLoginService        userLoginService;

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userLoginService);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/api/**", "/logout")
                    .authenticated()
                    .anyRequest()
                    .permitAll()
                    .and()
                    .headers()
                    .frameOptions()
                    .sameOrigin()
                    .httpStrictTransportSecurity()
                    .disable()
                    .and()
                    .formLogin()
                    .successHandler(loginSuccessHandler)
                    .failureHandler(loginFailureHandler)
                    .permitAll()
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(authEntryPoint)
                    .and()
                    .logout()
                    .deleteCookies("remove")
                    .invalidateHttpSession(false)
                    .logoutSuccessHandler(signoutSuccessHandler)
                    .permitAll();

            // CSRF tokens handling // http.csrf().disable();
            http.addFilterAfter(new CsrfTokenBindingRspHeaderFilter(), CsrfFilter.class);
        }
    }
}
