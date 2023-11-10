package com.banking.account.cmd.infrastructure;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.account.cmd.domain.EventStoreRepository;
import com.banking.cqrs.core.events.BaseAbstractEvent;
import com.banking.cqrs.core.events.EventModel;
import com.banking.cqrs.core.exceptions.AggregateNotFoundException;
import com.banking.cqrs.core.exceptions.ConcurrencyException;
import com.banking.cqrs.core.infrastructure.EventStore;
import com.banking.cqrs.core.producer.EventProducer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountEventStore implements EventStore {
    private final EventStoreRepository eventStoreRepository;
    private final EventProducer eventProducer;

    @Override
    public void save(String aggregateId, Iterable<BaseAbstractEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);

        if(expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException("Concurrency exception");
        }

        var version = expectedVersion;
        for(var event : events) {
            version++;
            event.setVersion(version);

            var eventModel = EventModel.builder()
                    .timeStamp(LocalDateTime.now())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .eventData(event)
                    .eventType(event.getClass().getTypeName())
                    .version(version)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);

            if(Objects.nonNull(persistedEvent.getId()) && persistedEvent.getId().isEmpty()) {
                // call kafka
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseAbstractEvent> getEvents(String aggregateId) {
        var eventStream =  eventStoreRepository.findByAggregateIdentifier(aggregateId);

        if (Objects.isNull(eventStream) || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Aggregate not found");
        }

        return eventStream.stream().map(EventModel::getEventData).collect(Collectors.toList());
    }
}
