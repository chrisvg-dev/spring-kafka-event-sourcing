package com.banking.cqrs.core.infrastructure;

import com.banking.cqrs.core.events.BaseAbstractEvent;
import com.banking.cqrs.core.events.EventModel;

import java.util.List;

public interface EventStore {
    void save(String aggregateId, Iterable<BaseAbstractEvent> events, int expectedVersion);
    List<BaseAbstractEvent> getEvents(String aggregateId);
}
