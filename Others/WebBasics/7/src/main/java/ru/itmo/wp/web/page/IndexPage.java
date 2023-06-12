package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @noinspection unused*/
public class IndexPage {
    ArticleService articleService = new ArticleService();
    UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        putMessage(request, view);
        findArticles(request, view);
    }

    private void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    private void findArticles(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAllNotHidden());
        addUsersMap(view);
    }

    private void addUsersMap(Map<String, Object> view) {
        Map<Long, User> userMap = new HashMap<>();
        for (User user : userService.findAll()) {
            userMap.put(user.getId(), new User(user));
        }
        view.put("users", userMap);
    }
}
