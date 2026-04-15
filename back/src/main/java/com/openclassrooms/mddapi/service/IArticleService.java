package com.openclassrooms.mddapi.service;

import java.util.List;

import com.openclassrooms.mddapi.model.Article;

public interface IArticleService {
    List<Article> getArticles();
}
