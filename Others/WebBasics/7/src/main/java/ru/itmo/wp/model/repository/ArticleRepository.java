package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;
import java.util.List;

public interface ArticleRepository {
    Article find(long id);
    List<Article> findByUserId(long userId);
    List<Article> findAll();
    List<Article> findAllNotHidden();
    void setArticleHidden(Article article, Boolean hidden);
    void save(Article article);
}
