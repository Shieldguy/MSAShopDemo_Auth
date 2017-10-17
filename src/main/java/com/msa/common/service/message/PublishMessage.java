package com.msa.common.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msa.common.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PublishMessage<T extends Message> {
    @Value(value = "${kafka.template.default-topic:msashop}")
    private String defaultTopic;

    @Autowired
    private KafkaTemplate<String, String>   kafkaTemplate;

    private MessageConverter<T> msgConverter = new MessageConverter();

    /**
     * Send message with default topic
     * @param msgObj
     * @throws JsonProcessingException
     */
    public void sendMessage(T msgObj) throws JsonProcessingException {
        sendMessage(defaultTopic, null, msgObj);
    }

    /**
     * Send message with specified topic
     * @param key
     * @param msgObj
     * @throws JsonProcessingException
     */
    public void sendMessage(String key, T msgObj) throws JsonProcessingException {
        sendMessage(defaultTopic, key, msgObj);
    }

    /**
     * Send message with key and specified topic
     * @param topicName
     * @param key
     * @param msgObj
     * @throws JsonProcessingException
     */
    public void sendMessage(String topicName, String key, T msgObj) throws JsonProcessingException {
        String payload = msgConverter.encode(msgObj);
        if(key != null)
            kafkaTemplate.send(topicName, key, msgConverter.encode(msgObj));
        else
            kafkaTemplate.send(topicName, msgConverter.encode(msgObj));

        if(log.isDebugEnabled())
            log.debug("Sent message to : {}/{} = {}", topicName, key, payload);
    }
}
