package com.banking.cqrs.core.commands;

import com.banking.cqrs.core.messages.AbstractMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseAbstractCommand extends AbstractMessage {
    public BaseAbstractCommand(String id) {
        super(id);
    }
}
