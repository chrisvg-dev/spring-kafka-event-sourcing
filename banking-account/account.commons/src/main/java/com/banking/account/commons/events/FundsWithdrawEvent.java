package com.banking.account.commons.events;

import com.banking.cqrs.core.events.BaseAbstractEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FundsWithdrawEvent extends BaseAbstractEvent {
    private BigDecimal amount;
}
