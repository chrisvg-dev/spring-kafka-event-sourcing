package com.banking.account.commons.events;

import com.banking.account.commons.dto.AccountType;
import com.banking.cqrs.core.events.BaseAbstractEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AccountOpenedEvent extends BaseAbstractEvent {
    private String accountHolder;
    private AccountType accountType;
    private LocalDate createdAt;
    private BigDecimal openingBalance;
}