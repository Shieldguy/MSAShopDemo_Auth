package com.msa.auth.service.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class MessageListener {
    private CountDownLatch latch = new CountDownLatch(3);

    public CountDownLatch getLatch() {
        return latch;
    }

    /*
    @KafkaListener(topics = "msashop", group = "auth")
    public void listenMessage(@Payload String message,
                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        if(log.isDebugEnabled())
            log.debug("listenMessage :: receive messsage ({}) = {}", topic, message);

        latch.countDown();
    }
    */

    @KafkaListener(topics = "msashop", group = "auth")
    public void listenMessageWithKey(@Payload String message,
                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        if(log.isDebugEnabled())
            log.debug("listenMessage :: receive messsage ({}/{}) = {}", topic, key, message);

        latch.countDown();
    }

    /*
    @KafkaListener(topicPartitions =
        @TopicPartition(topic = "msashop", partitions = { "0", "1" }))
    public void listenMessageWithPartition(@Payload String message,
                                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                          @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                                          @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.debug("listenMessageWithPartition :: receive messsage ({}/{}/{})", topic, key, partition);
    }
    */
}
