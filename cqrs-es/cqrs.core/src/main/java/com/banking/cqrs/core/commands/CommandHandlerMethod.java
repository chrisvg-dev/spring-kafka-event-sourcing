package com.banking.cqrs.core.commands;

@FunctionalInterface // Only 1 abstract method
public interface CommandHandlerMethod<T extends BaseAbstractCommand> {
    void handle(T command);
}