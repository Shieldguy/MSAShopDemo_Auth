package com.msa.auth.domain;

import com.msa.auth.domain.types.ResultString;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseData {
    private Integer code;
    private ResultString result;
    private String  message;
}
