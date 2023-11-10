package com.banking.account.commons.events;

import com.banking.cqrs.core.events.BaseAbstractEvent;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AccountClosedEvent extends BaseAbstractEvent {
}
