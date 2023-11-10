package com.banking.account.cmd.infrastructure;

import com.banking.cqrs.core.commands.BaseAbstractCommand;
import com.banking.cqrs.core.commands.CommandHandlerMethod;
import com.banking.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
    private final Map<Class<? extends BaseAbstractCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseAbstractCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handlerMethod) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handlerMethod);
    }

    @Override
    public void send(BaseAbstractCommand command) {
        var handlers = routes.get(command.getClass());

        if (handlers == null || handlers.isEmpty()) {
            throw new IllegalArgumentException("No handler registered for " + command.getClass());
        }

        if (handlers.size() > 1) {
            throw new IllegalArgumentException("Too many handlers");
        }

        handlers.get(0).handle(command);
    }
}
