package com.openclassrooms.mddapi.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.ArticleCreateDTO;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;

@Service
public class ArticleService implements IArticleService {

	private final IThemeService themeService;
    private final IUserService userService;
    private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository, IUserService userService, IThemeService themeService) {
		this.articleRepository = articleRepository;
        this.userService = userService;
        this.themeService = themeService;
	}

	public List<Article> getArticles(User user) {
		List<Theme> themes = user.getThemes();
		if (themes.isEmpty()) {
			return Collections.emptyList();
		}
		return this.articleRepository.findAllByThemeIn(themes);
	}

	public Article findById(Long id) {
		Article article = articleRepository.findById(id).orElse(null);
		return article;
	}

	public void create(String username, ArticleCreateDTO articleCreateDTO) {
		User user = this.userService.findByUsername(username);
		Theme theme = this.themeService.findById(articleCreateDTO.getTheme());
		Article article = new Article();
		article.setTitre(articleCreateDTO.getTitre());
		article.setAuteur(user);
		article.setTexte(articleCreateDTO.getTexte());
		article.setTheme(theme);
		this.articleRepository.save(article);
	}
}