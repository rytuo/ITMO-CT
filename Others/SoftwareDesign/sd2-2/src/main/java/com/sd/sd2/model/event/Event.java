package com.sd.sd2.model.event;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table
public class Event {

    public static Event create(EventType eventType, long subscriptionId) {
        var event = new Event();
        event.eventType = eventType;
        event.subscriptionId = subscriptionId;
        event.timestamp = new Timestamp(System.currentTimeMillis());
        return event;
    }

    @Id
    @GeneratedValue
    private long id;

    private long subscriptionId;

    private EventType eventType;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timestamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
