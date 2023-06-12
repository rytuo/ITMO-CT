package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage {
    ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("articles", articleService.findByUserId(user.getId()));
        }
    }

    private void setArticleHidden(HttpServletRequest request, Map<String, Object> view) {
        Article article = articleService.find(Long.parseLong(request.getParameter("article_id")));
        User user = (User) request.getSession().getAttribute("user");
        Boolean hidden = Boolean.parseBoolean(request.getParameter("hidden"));
        if (article != null && user != null && article.getUserId() == user.getId()) {
            articleService.setArticleHidden(article, hidden);
            view.put("newHidden", article.getHidden());
        }
    }
}