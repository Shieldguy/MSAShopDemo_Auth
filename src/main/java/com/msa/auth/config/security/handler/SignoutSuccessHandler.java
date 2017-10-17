package com.msa.auth.config.security.handler;

import com.msa.common.model.AuditMessage;
import com.msa.common.service.LoggingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class SignoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private LoggingService loggingService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        loggingService.logging(AuditMessage.builder()
                .timestamp(LocalDateTime.now())
                .clientIp(request.getRemoteAddr())
                .targetUrl(request.getRequestURL().toString())
                .userName(authentication.getName())
                .message("User logged out")
                .build());

        if(authentication.getPrincipal() != null) {
            // TODO. remove session data
            HttpSession session = request.getSession(true);
            // session.setAttribute(SESSION_DATA, null);
        }
    }
}
