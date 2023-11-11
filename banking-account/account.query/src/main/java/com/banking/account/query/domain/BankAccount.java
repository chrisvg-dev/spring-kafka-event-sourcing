package com.banking.account.query.domain;

import com.banking.account.commons.dto.AccountType;
import com.banking.cqrs.core.domain.BaseAbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BankAccount extends BaseAbstractEntity {
    @Id
    private String id;
    private String accountHolder;
    private LocalDateTime createdAt;
    private AccountType accountType;
    private BigDecimal balance;
}
