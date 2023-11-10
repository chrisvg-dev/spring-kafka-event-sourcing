package com.banking.account.cmd.api.command;

public interface CommandHandler {
    void handle(OpenAccountCommand command);
    void handle(DepositFundsCommand command);
    void handle(WithDrawFundsCommand command);
    void handle(CloseAccountCommand command);
}
