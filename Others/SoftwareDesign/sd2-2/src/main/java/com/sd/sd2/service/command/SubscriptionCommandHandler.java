package com.sd.sd2.service.command;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sd.sd2.model.command.CreateSubscriptionCommand;
import com.sd.sd2.model.command.UpdateSubscriptionCommand;
import com.sd.sd2.model.event.Event;
import com.sd.sd2.model.event.EventType;
import com.sd.sd2.repository.event.EventRepositoryDALImpl;

@Service
public class SubscriptionCommandHandler {

    private final EventRepositoryDALImpl eventRepository;

    @Autowired
    public SubscriptionCommandHandler(EventRepositoryDALImpl eventRepository) {
        this.eventRepository = eventRepository;
    }

    public long createSubscription(CreateSubscriptionCommand command) {
        Event event = eventRepository.save(Event.create(EventType.CREATE_SUBSCRIPTION, generateId()));
        return event.getSubscriptionId();
    }

    public void updateSubscription(UpdateSubscriptionCommand command) {
        if (eventRepository.isNotSubscriptionExists(command.id())) {
            throw new RuntimeException("Subscription does not exist");
        }

        eventRepository.save(Event.create(EventType.UPDATE_SUBSCRIPTION, command.id()));
    }

    private long generateId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
