package com.banking.cqrs.core.events;

import com.banking.cqrs.core.messages.AbstractMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseAbstractEvent extends AbstractMessage {
    private int version;
}
