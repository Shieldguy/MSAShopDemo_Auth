package com.msa.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msa.common.model.AuditMessage;
import com.msa.common.service.message.PublishMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoggingService {
    @Autowired
    private PublishMessage<AuditMessage>    publishMessage;

    public void logging(AuditMessage msg) {
        try {
            publishMessage.sendMessage("logging", msg);
            //publishMessage.sendMessage(msg);
        } catch (JsonProcessingException ex) {
            log.error("Failed to send logging message : {}", ex.getMessage());
        }
    }
}
