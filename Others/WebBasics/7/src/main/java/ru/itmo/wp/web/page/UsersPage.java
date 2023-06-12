package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("user", user);
        }
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user", userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void setUserAdmin(HttpServletRequest request, Map<String, Object> view) {
        User user = userService.find(Long.parseLong(request.getParameter("userId")));
        User current = (User) request.getSession().getAttribute("user");
        Boolean admin = Boolean.parseBoolean(request.getParameter("admin"));
        if (user != null && current != null && current.getAdmin()) {
            userService.setAdmin(user, admin);
            if (current.getId() == user.getId()) {
                request.getSession().setAttribute("user", user);
                throw new RedirectException("/index");
            }
            view.put("newAdminState", user.getAdmin());
        }
    }
}
