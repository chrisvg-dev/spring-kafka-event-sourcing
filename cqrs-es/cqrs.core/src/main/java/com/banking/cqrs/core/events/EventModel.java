package com.banking.cqrs.core.events;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collation = "eventStore") // This annotation is used to indicate that the class is a domain object that we want to persist to the database.
public class EventModel {
    @Id
    private String id;
    private LocalDateTime timeStamp;
    private String aggregateIdentifier;
    private String aggregateType;
    private int version;
    private String eventType;
    private BaseAbstractEvent eventData;
}