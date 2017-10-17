package com.msa.common.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuditMessage extends Message {
    private LocalDateTime   timestamp;

    private String  targetUrl;
    private String  clientIp;

    private String  userName;

    private String  message;
}
