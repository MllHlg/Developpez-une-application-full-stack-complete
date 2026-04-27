package com.openclassrooms.mddapi.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.ArticleCreateDTO;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
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

	/**
	 * Récupère la liste des articles liés aux thèmes auxquels l'utilisateur est
	 * abonné.
	 * * @param user L'utilisateur actuellement authentifié
	 * 
	 * @return Une liste d'articles, ou une liste vide si l'utilisateur n'a aucun
	 *         abonnement
	 */
	public List<Article> getArticles(User user) {
		List<Theme> themes = user.getThemes();
		if (themes.isEmpty()) {
			return Collections.emptyList();
		}
		return this.articleRepository.findAllByThemeIn(themes);
	}

	/**
	 * Récupère l'article correspondant à l'identifiant donné en paramètre
	 * * @param id L'identifiant de l'article à trouver
	 * 
	 * @return l'article s'il existe et lève une exception dans le cas où il n'est
	 *         pas présent
	 */
	public Article findById(Long id) {
		Article article = articleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Article non trouvé"));
		return article;
	}

	/**
	 * Créer un nouvel article avec l'utilisateur authentifié comme auteur
	 * * @param username le nom de l'utilisateur actuellement authentifié
	 * * @param articleCreateDTO les données de l'article envoyées à l'API
	 */
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