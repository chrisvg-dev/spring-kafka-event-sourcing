package com.banking.cqrs.core.domain;

import com.banking.cqrs.core.events.BaseAbstractEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public abstract class AggregateRoot {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    protected String id;
    private int version = -1;
    private final List<BaseAbstractEvent> changes = new ArrayList<>();

    public int getID() {
        return this.version;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<BaseAbstractEvent> getUncommitedChanges() {
        return this.changes;
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChange(BaseAbstractEvent event, Boolean isNew) {
        try {
            var method = this.getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
            logger.warn(MessageFormat.format("Missing apply method for {0}", event.getClass().getName()));
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (isNew) {
                this.changes.add(event);
            }
        }
    }

    public void raiseEvent(BaseAbstractEvent event) {
        this.applyChange(event, true);
    }

    public void loadFromHistory(List<BaseAbstractEvent> history) {
        history.forEach(event -> this.applyChange(event, false));
    }
}