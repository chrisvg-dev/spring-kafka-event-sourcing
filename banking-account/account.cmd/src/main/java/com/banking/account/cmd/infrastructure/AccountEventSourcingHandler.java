package com.banking.account.cmd.infrastructure;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.cqrs.core.domain.AggregateRoot;
import com.banking.cqrs.core.handler.EventSourcingHandler;
import com.banking.cqrs.core.infrastructure.EventStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@AllArgsConstructor
@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {
    private final EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregateRoot) {
        eventStore.save(aggregateRoot.getID(), aggregateRoot.getUncommitedChanges(), aggregateRoot.getVersion());
        aggregateRoot.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);

        if(!events.isEmpty() || events != null) {
            aggregate.loadFromHistory(events);
            var latestVersion = events.stream().map(e -> e.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }

        return aggregate;
    }
}
