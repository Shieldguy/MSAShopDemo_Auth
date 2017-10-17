package com.msa.common.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class KafkaConsumerConfigTemplate {
    /**
     * Return consumerFactory.
     * Dev. Comment. : If you use this, then should define with Bean.
     * @return
     */
    public abstract ConsumerFactory<String, String> consumerFactory();

    /**
     * Return ConrainerFactory.
     * Dev. Comment: If you use this, then should define with Bean.
     * @return
     */
    public abstract ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory();

    protected ConsumerFactory<String, String> consumerFactoryTemplate(String bootStrapServers, String groupId) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    protected ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactoryTemplate() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
