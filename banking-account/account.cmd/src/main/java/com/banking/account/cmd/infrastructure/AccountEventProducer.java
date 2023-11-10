package com.banking.account.cmd.infrastructure;

import com.banking.cqrs.core.events.BaseAbstractEvent;
import com.banking.cqrs.core.producer.EventProducer;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountEventProducer implements EventProducer {
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Override
    public void produce(String topic, BaseAbstractEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
