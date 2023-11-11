package com.banking.account.query.domain;

import com.banking.cqrs.core.domain.BaseAbstractEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<BankAccount, String> {
    Optional<BankAccount> findByAccountHolder(String accountHolder);
    List<BaseAbstractEntity> findByBalanceGreaterThan(BigDecimal balance);
    List<BaseAbstractEntity> findByBalanceLessThan(BigDecimal balance);
}
