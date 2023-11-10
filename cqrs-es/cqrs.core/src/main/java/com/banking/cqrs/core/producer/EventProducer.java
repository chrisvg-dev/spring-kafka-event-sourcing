package com.banking.cqrs.core.producer;

import com.banking.cqrs.core.events.BaseAbstractEvent;

public interface EventProducer {
    void produce(String topic, BaseAbstractEvent event);
}
