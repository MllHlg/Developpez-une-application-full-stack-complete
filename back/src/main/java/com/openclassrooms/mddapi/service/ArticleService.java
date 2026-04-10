package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.repository.ArticleRepository;

public class ArticleService implements IArticleService {

	private ArticleRepository articleRepository;
	
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}
	
}
