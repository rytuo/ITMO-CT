package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.domain.types;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;
import ru.itmo.wp.web.exception.RedirectException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class EnterPage extends Page {
    EventRepository eventRepository = new EventRepositoryImpl();

    private void enter(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String loginOrEmail = request.getParameter("loginOrEmail");
        String password = request.getParameter("password");

        userService.validateEnter(loginOrEmail, password);
        User user = userService.findByLoginOrEmailAndPassword(loginOrEmail, password);
        setUser(user);

        Event event = new Event();
        event.setUserId(user.getId());
        event.setType(types.ENTER);
        eventRepository.save(event);
        setMessage("Hello, " + user.getLogin());

        throw new RedirectException("/index");
    }
}
