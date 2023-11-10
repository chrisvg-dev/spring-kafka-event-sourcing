package com.banking.account.cmd.api.command;

import com.banking.cqrs.core.commands.BaseAbstractCommand;

public class CloseAccountCommand extends BaseAbstractCommand {
    public CloseAccountCommand(String id) {
        super(id);
    }
}