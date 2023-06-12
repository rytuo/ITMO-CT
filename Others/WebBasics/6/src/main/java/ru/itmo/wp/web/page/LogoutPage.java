package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.domain.types;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class LogoutPage extends Page {
    EventRepository eventRepository = new EventRepositoryImpl();
    @Override
    void action(HttpServletRequest request, Map<String, Object> view) {
        Event event = new Event();
        User user = ((User) request.getSession().getAttribute("user"));
        event.setUserId(user.getId());
        event.setType(types.LOGOUT);
        eventRepository.save(event);
        request.getSession().removeAttribute("user");
        setMessage("Good bye. Hope to see you soon!");
        throw new RedirectException("/index");
    }
}
