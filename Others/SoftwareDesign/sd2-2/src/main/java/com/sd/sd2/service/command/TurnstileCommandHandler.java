package com.sd.sd2.service.command;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.sd.sd2.model.command.EnterIfAvailableCommand;
import com.sd.sd2.model.command.ExitCommand;
import com.sd.sd2.model.event.Event;
import com.sd.sd2.model.event.EventType;
import com.sd.sd2.repository.event.EventRepositoryDAL;

@Service
public class TurnstileCommandHandler {

    private final EventRepositoryDAL eventRepository;

    @Autowired
    public TurnstileCommandHandler(EventRepositoryDAL eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void enterIfAvailable(EnterIfAvailableCommand command) {
        if (eventRepository.isNotSubscriptionExists(command.id())) {
            throw new RuntimeException("Subscription does not exist");
        }

        Optional<Event> enterEvent = eventRepository.getLatestEventByType(command.id(), EventType.ENTER);
        Optional<Event> exitEvent = eventRepository.getLatestEventByType(command.id(), EventType.EXIT);

        // пускаем только если последний ивент был выход или ивенты отсутствуют
        boolean lastEventIsExit = exitEvent.isPresent() && (
                enterEvent.isEmpty() ||
                        enterEvent.get().getTimestamp().before(exitEvent.get().getTimestamp())
        );
        boolean noEvents = exitEvent.isEmpty() && enterEvent.isEmpty();

        if (lastEventIsExit || noEvents) {
            eventRepository.save(Event.create(EventType.ENTER, command.id()));
        } else {
            throw new RuntimeException("Вы и так внутри");
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void exit(ExitCommand command) {
        if (eventRepository.isNotSubscriptionExists(command.id())) {
            throw new RuntimeException("Subscription does not exist");
        }

        Optional<Event> enterEvent = eventRepository.getLatestEventByType(command.id(), EventType.ENTER);
        Optional<Event> exitEvent = eventRepository.getLatestEventByType(command.id(), EventType.EXIT);

        // пускаем только если последний ивент был вход или ивенты отсутствуют
        boolean lastEventIsExit = enterEvent.isPresent() && (
                exitEvent.isEmpty() ||
                        exitEvent.get().getTimestamp().before(enterEvent.get().getTimestamp())
        );
        boolean noEvents = exitEvent.isEmpty() && enterEvent.isEmpty();

        if (lastEventIsExit || noEvents) {
            eventRepository.save(Event.create(EventType.EXIT, command.id()));
        } else {
            throw new RuntimeException("Вы и так вышли");
        }
    }
}
