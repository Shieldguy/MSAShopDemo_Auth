package com.msa.common.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.common.model.Message;
import lombok.Data;

import java.io.IOException;
import java.nio.charset.Charset;

public class MessageConverter<T extends Message> {
    protected ObjectMapper mapper = new ObjectMapper();

    public String encode(T payload) throws JsonProcessingException {
        MsgData message = new MsgData();
        message.setName(payload.getClass().getName());
        message.setPayload(mapper.writeValueAsString(payload).getBytes(Charset.forName("UTF-8")));
        return mapper.writeValueAsString(message);
    }

    public T decode(String msg) throws IOException, ClassNotFoundException {
        MsgData message = mapper.readValue(msg, MsgData.class);
        return (T)mapper.readValue(message.getPayload(), Class.forName(message.getName()));
    }

    @Data
    public class MsgData {
        private String  name;
        private byte[]  payload;
    }
}
