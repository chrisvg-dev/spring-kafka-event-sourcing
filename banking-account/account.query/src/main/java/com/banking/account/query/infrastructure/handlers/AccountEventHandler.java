package com.banking.account.query.infrastructure.handlers;

import com.banking.account.commons.events.AccountClosedEvent;
import com.banking.account.commons.events.AccountOpenedEvent;
import com.banking.account.commons.events.FundsDepositedEvent;
import com.banking.account.commons.events.FundsWithdrawEvent;
import com.banking.account.query.domain.AccountRepository;
import com.banking.account.query.domain.BankAccount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountEventHandler implements EventHandler {
    private final AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .createdAt(event.getCreatedAt())
                .build();

        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        var bankAccount = accountRepository.findById(event.getId()).orElseThrow(() -> new RuntimeException("Account not found"));
        var currentBalance = bankAccount.getBalance();
        var latestBalance = currentBalance.add(event.getAmount());
        bankAccount.setBalance(latestBalance);
        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundsWithdrawEvent event) {
        var bankAccount = accountRepository.findById(event.getId()).orElseThrow(() -> new RuntimeException("Account not found"));
        if(bankAccount.getBalance().compareTo(event.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        var currentBalance = bankAccount.getBalance();
        var latestBalance = currentBalance.subtract(event.getAmount());
        bankAccount.setBalance(latestBalance);
    }

    @Override
    public void on(AccountClosedEvent event) {
        var bankAccount = accountRepository.findById(event.getId()).orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepository.deleteById(bankAccount.getId());
    }
}
