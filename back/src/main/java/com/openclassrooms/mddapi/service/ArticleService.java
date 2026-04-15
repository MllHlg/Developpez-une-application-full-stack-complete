package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.repository.ArticleRepository;

@Service
public class ArticleService implements IArticleService {

	private ArticleRepository articleRepository;
	
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public List<Article> getArticles() {
		return this.articleRepository.findAll();
	}

	public Article findById(Long id) {
		Article article = articleRepository.findById(id).orElse(null);
		return article;
	}
	
}
