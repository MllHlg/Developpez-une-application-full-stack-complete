package com.openclassrooms.mddapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.dto.ArticleDetailDTO;
import com.openclassrooms.mddapi.mapper.ArticleDetailMapper;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.service.IArticleService;
import com.openclassrooms.mddapi.service.ICommentService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ICommentService commentService;
    private final IArticleService articleService;
    private final ArticleMapper articleMapper;
    private final ArticleDetailMapper articleDetailMapper;

    public ArticleController(IArticleService articleService, ArticleMapper articleMapper, ArticleDetailMapper articleDetailMapper, ICommentService commentService) {
        this.articleService = articleService;
        this.articleMapper = articleMapper;
        this.articleDetailMapper = articleDetailMapper;
        this.commentService = commentService;
    }

    @GetMapping()
    public ResponseEntity<List<ArticleDTO>> getArticles() {
		List<Article> articles = this.articleService.getArticles();
		return ResponseEntity.ok().body(articleMapper.toDto(articles));
	}

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDetailDTO> getArticleById(@PathVariable("id") final Long id) {
        Article article = this.articleService.findById(id);
        ArticleDetailDTO dto = this.articleDetailMapper.toDto(article);
        dto.setCommentaires(this.commentService.getComments(id));
        return ResponseEntity.ok(dto);
    }

}
