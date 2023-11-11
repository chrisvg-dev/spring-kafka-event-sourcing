package com.banking.account.query.infrastructure.handlers;

import com.banking.account.commons.events.AccountClosedEvent;
import com.banking.account.commons.events.AccountOpenedEvent;
import com.banking.account.commons.events.FundsDepositedEvent;
import com.banking.account.commons.events.FundsWithdrawEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawEvent event);
    void on(AccountClosedEvent event);
}
