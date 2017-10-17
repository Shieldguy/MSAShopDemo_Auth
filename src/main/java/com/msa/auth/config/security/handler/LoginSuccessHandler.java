package com.msa.auth.config.security.handler;

import com.msa.common.model.AuditMessage;
import com.msa.common.service.LoggingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private LoggingService loggingService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if(log.isDebugEnabled())
            log.debug("Authentication success : {} / {} from {}", request.getRequestURL().toString(),
                    authentication.getName(), request.getRemoteAddr());

        loggingService.logging(AuditMessage.builder()
                .timestamp(LocalDateTime.now())
                .clientIp(request.getRemoteAddr())
                .targetUrl(request.getRequestURL().toString())
                .userName(authentication.getName())
                .message("User logged in")
                .build());

        if(authentication.getPrincipal() != null) {
            // TODO. set session data
            HttpSession session = request.getSession();
            // session.setAttribute(SESSION_DATA, null);
        }

        clearAuthenticationAttributes(request);
    }
}
