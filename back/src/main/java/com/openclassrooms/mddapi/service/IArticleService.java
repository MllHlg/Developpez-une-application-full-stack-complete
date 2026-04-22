package com.openclassrooms.mddapi.service;

import java.util.List;

import com.openclassrooms.mddapi.dto.ArticleCreateDTO;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.User;

public interface IArticleService {
    List<Article> getArticles(User user);
    Article findById(Long id);
    void create(String username, ArticleCreateDTO articleCreateDTO);
}
