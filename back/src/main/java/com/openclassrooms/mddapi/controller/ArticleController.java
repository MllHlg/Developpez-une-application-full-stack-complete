package com.openclassrooms.mddapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.service.IArticleService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final IArticleService articleService;
    private final ArticleMapper articleMapper;

    public ArticleController(IArticleService articleService, ArticleMapper articleMapper) {
        this.articleService = articleService;
        this.articleMapper = articleMapper;
    }

    @GetMapping()
    public ResponseEntity<List<ArticleDTO>> getThemes() {
		List<Article> articles = this.articleService.getArticles();
		return ResponseEntity.ok().body(articleMapper.toDto(articles));
	}
    
}
