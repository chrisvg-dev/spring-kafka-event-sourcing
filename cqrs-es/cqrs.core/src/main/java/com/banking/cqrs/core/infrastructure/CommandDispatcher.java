package com.banking.cqrs.core.infrastructure;

import com.banking.cqrs.core.commands.BaseAbstractCommand;
import com.banking.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseAbstractCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handlerMethod);
    void send(BaseAbstractCommand command);
}
