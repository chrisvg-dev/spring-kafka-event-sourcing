package com.banking.account.cmd.domain;

import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.commons.events.AccountClosedEvent;
import com.banking.account.commons.events.AccountOpenedEvent;
import com.banking.account.commons.events.FundsDepositedEvent;
import com.banking.account.commons.events.FundsWithdrawEvent;
import com.banking.cqrs.core.domain.AggregateRoot;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class AccountAggregate extends AggregateRoot {
    private Boolean active;
    private BigDecimal balance;

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(BigDecimal amount) {
        if (!this.active) {
            throw new IllegalStateException("Account is not active");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        raiseEvent(FundsDepositedEvent.builder().id(this.id).amount(amount).build());
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance = this.balance.add(event.getAmount());
    }

    public void withdrawFunds(BigDecimal amount) {
        if (!this.active) {
            throw new IllegalStateException("Account is not active");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (amount.compareTo(this.balance) > 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        raiseEvent(FundsWithdrawEvent.builder().id(this.id).amount(amount).build());
    }

    public void apply(FundsWithdrawEvent event) {
        this.id = event.getId();
        this.balance = this.balance.subtract(event.getAmount());
    }

    public void closeAccount() {
        if (!this.active) {
            throw new IllegalStateException("Account is not active");
        }
        raiseEvent(AccountClosedEvent.builder().id(this.id).build());
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }
}
