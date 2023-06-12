package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Page {
    final UserService userService = new UserService();
    HttpServletRequest request = null;

    void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }

    void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;
        view.put("userCount", userService.findCount());

        User user = getUser();
        if (user != null) {
            view.put("user", user);
        }

        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    void after(HttpServletRequest request, Map<String, Object> view) {

    }
}