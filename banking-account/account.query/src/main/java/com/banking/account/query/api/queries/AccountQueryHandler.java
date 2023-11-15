package com.banking.account.query.api.queries;

import com.banking.account.query.api.dto.EqualityType;
import com.banking.account.query.domain.AccountRepository;
import com.banking.account.query.domain.BankAccount;
import com.banking.cqrs.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountQueryHandler implements QueryHandler {
    private final AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        Iterable<BankAccount> accounts = accountRepository.findAll();
        List<BaseEntity> result = new ArrayList<>();
        accounts.forEach(result::add);
        return result;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        var account = accountRepository.findById(query.getId());

        if (account.isPresent()) {
            List<BaseEntity> result = new ArrayList<>();
            result.add(account.get());
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        var account = accountRepository.findByAccountHolder(query.getAccountHolder());
        if (account.isPresent()) {
            List<BaseEntity> result = new ArrayList<>();
            result.add(account.get());
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
        return EqualityType.GREATER_THAN.equals(query.getEqualityType()) ?
                accountRepository.findByBalanceGreaterThan(query.getBalance().doubleValue()) :
                accountRepository.findByBalanceLessThan(query.getBalance().doubleValue());
    }
}
