package com.banking.account.commons.events;

import com.banking.cqrs.core.events.BaseAbstractEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FundsDepositedEvent extends BaseAbstractEvent {
    private BigDecimal amount;
}
