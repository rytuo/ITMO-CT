package com.sd.sd2.service.query;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sd.sd2.model.event.Event;
import com.sd.sd2.model.event.EventType;
import com.sd.sd2.model.query.GetSubscriptionInfoQuery;
import com.sd.sd2.repository.event.EventRepository;
import com.sd.sd2.repository.event.EventRepositoryDALImpl;

import jakarta.transaction.Transactional;

@Service
public class SubscriptionsQueryHandler {

    private final EventRepositoryDALImpl eventRepository;

    @Autowired
    public SubscriptionsQueryHandler(EventRepositoryDALImpl eventRepository) {
        this.eventRepository = eventRepository;
    }

    public String getSubscriptionInfo(GetSubscriptionInfoQuery query) {
        Event event = eventRepository.getSubscriptionEvent(query.id())
                .orElseThrow(() -> new RuntimeException("Subscription does not exist"));
        Date subscriptionCreationDate = new Date(event.getTimestamp().getTime());
        return String.format("Created at %s", subscriptionCreationDate);
    }
}
