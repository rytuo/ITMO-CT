package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findAllNotHidden() {
        return articleRepository.findAllNotHidden();
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public List<Article> findByUserId(long userId) {
        return articleRepository.findByUserId(userId);
    }

    public void validateArticle(Article article) throws ValidationException {
        if (new UserService().find(article.getUserId()) == null) {
            throw new ValidationException("Not existing user");
        }

        if (Strings.isNullOrEmpty(article.getTitle())) {
            throw new ValidationException("Title required");
        }

        if (Strings.isNullOrEmpty(article.getText())) {
            throw new ValidationException("Text required");
        }
    }

    public void createArticle(Article article) {
        articleRepository.save(article);
    }

    public void setArticleHidden(Article article, Boolean hidden) {
        articleRepository.setArticleHidden(article, hidden);
    }
}
