package com.banking.account.cmd.api.command;

import com.banking.cqrs.core.commands.BaseAbstractCommand;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithDrawFundsCommand extends BaseAbstractCommand {
    private BigDecimal amount;
}