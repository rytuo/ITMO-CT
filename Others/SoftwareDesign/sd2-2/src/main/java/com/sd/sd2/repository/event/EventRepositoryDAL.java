package com.sd.sd2.repository.event;

import java.util.List;
import java.util.Optional;

import com.sd.sd2.model.event.Event;
import com.sd.sd2.model.event.EventType;

public interface EventRepositoryDAL {

    Event save(Event event);

    boolean isNotSubscriptionExists(long subscriptionId);

    Optional<Event> getSubscriptionEvent(long subscriptionId);

    Optional<Event> getLatestEventByType(long subscriptionId, EventType eventType);

    List<Event> getAll();
}
