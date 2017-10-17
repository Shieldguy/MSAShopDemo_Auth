package com.msa.auth.config.message;

import com.msa.auth.service.message.MessageListener;
import com.msa.common.config.KafkaConsumerConfigTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableKafka
@Slf4j
public class KafkaConsumerConfig extends KafkaConsumerConfigTemplate {
    @Value(value = "${kafka.bootstrap-servers:localhost:9092}")
    private String  bootStrapServers;
    @Value(value = "${kafka.consumer.group-id:0}")
    private String  groupId;

    /**
     * Return consumerFactory.
     * Dev. Comment. : If you use this, then should define with Bean.
     *
     * @return
     */
    @Bean
    @Override
    public ConsumerFactory<String, String> consumerFactory() {
        return this.consumerFactoryTemplate(bootStrapServers, groupId);
    }

    /**
     * Return ConrainerFactory.
     * Dev. Comment: If you use this, then should define with Bean.
     *
     * @return
     */
    @Bean
    @Override
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                this.kafkaListenerContainerFactoryTemplate();
        //factory.setRecordFilterStrategy(record -> record.value().contains("msashop"));
        return factory;
    }

    @Bean
    public MessageListener listener() {
        MessageListener listener = new MessageListener();
        try {
            listener.getLatch().await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            log.error("Listener Latch Exception : {}", ex.getMessage());
        }
        return listener;
    }
}
