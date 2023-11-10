package com.banking.account.cmd.api.command;

import com.banking.account.commons.dto.AccountType;
import com.banking.cqrs.core.commands.BaseAbstractCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class OpenAccountCommand extends BaseAbstractCommand {
    private String accountHolder;
    private AccountType accountType;
    private BigDecimal openingBalance;
}
