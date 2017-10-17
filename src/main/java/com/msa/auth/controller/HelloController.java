package com.msa.auth.controller;

import com.msa.auth.domain.ResponseData;
import com.msa.auth.domain.types.ResultString;
import com.msa.common.service.LoggingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequestMapping("/api")
@RestController()
@Slf4j
public class HelloController {
    @Autowired
    private LoggingService loggingService;

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData> hello(Principal principal) {
        ResponseData rspData = null;

        log.debug("hello() called");

        if(principal == null || principal.getName() == null) {
            rspData = ResponseData.builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .result(ResultString.FAIL)
                    .message("Un-authorized request")
                    .build();
        } else {
            rspData = ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .result(ResultString.SUCCESS)
                    .message(principal.getName())
                    .build();
        }

        log.debug("hello() end with : {}", rspData.toString());
        return new ResponseEntity(rspData, HttpStatus.OK);
    }
}
