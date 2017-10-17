package com.msa.auth.config.security.handler;

import com.msa.common.model.AuditMessage;
import com.msa.common.service.LoggingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private LoggingService  loggingService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        if(log.isDebugEnabled())
            log.debug("Authentication failed : {} / {} from {}", request.getRequestURL().toString(),
                    exception.getMessage(), request.getRemoteAddr());

        loggingService.logging(AuditMessage.builder()
                .timestamp(LocalDateTime.now())
                .clientIp(request.getRemoteAddr())
                .targetUrl(request.getRequestURL().toString())
                .message(exception.getMessage())
                .build());

        super.onAuthenticationFailure(request, response, exception);
    }
}
