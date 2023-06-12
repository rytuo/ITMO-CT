package ru.itmo.wp.web.page;

import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class UsersPage extends Page {
    @Override
    void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }
}
