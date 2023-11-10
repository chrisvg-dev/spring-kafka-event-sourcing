package com.banking.account.cmd.api.command;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.cqrs.core.handler.EventSourcingHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountCommandHandler implements CommandHandler {
    private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;
    @Override
    public void handle(OpenAccountCommand command) {
        var accountAggregate = new AccountAggregate(command);
        eventSourcingHandler.save(accountAggregate);
    }

    @Override
    public void handle(DepositFundsCommand command) {
        var accountAggregate = eventSourcingHandler.getById(command.getId());
        accountAggregate.depositFunds(command.getAmount());
        eventSourcingHandler.save(accountAggregate);
    }

    @Override
    public void handle(WithDrawFundsCommand command) {
        var accountAggregate = eventSourcingHandler.getById(command.getId());
        if (accountAggregate.getBalance().compareTo(command.getAmount()) < 0) {
            throw new IllegalStateException("Insufficient funds in account");
        }
        accountAggregate.withdrawFunds(command.getAmount());
        eventSourcingHandler.save(accountAggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        var accountAggregate = eventSourcingHandler.getById(command.getId());
        accountAggregate.closeAccount();
        eventSourcingHandler.save(accountAggregate);
    }
}
