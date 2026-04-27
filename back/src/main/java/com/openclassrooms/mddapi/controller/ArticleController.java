package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.service.IUserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.ArticleCreateDTO;
import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.dto.ArticleDetailDTO;
import com.openclassrooms.mddapi.dto.CommentCreateDTO;
import com.openclassrooms.mddapi.mapper.ArticleDetailMapper;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.IArticleService;
import com.openclassrooms.mddapi.service.ICommentService;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final IUserService userService;
    private final ICommentService commentService;
    private final IArticleService articleService;
    private final ArticleMapper articleMapper;
    private final ArticleDetailMapper articleDetailMapper;

    public ArticleController(IArticleService articleService, ArticleMapper articleMapper,
            ArticleDetailMapper articleDetailMapper, ICommentService commentService, IUserService userService) {
        this.articleService = articleService;
        this.articleMapper = articleMapper;
        this.articleDetailMapper = articleDetailMapper;
        this.commentService = commentService;
        this.userService = userService;
    }

    /**
     * Renvoie les articles liés aux thèmes auquels l'utilisateur authentifié est
     * abonné
     * * @param authentication Token de l'utilisateur actuellement authentifié
     * 
     * @return Une liste d'articles
     */
    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getArticles(Authentication authentication) {
        User user = this.userService.findByUsername(authentication.getName());
        List<Article> articles = this.articleService.getArticles(user);
        return ResponseEntity.ok().body(articleMapper.toDto(articles));
    }

    /**
     * Renvoie les informations de l'article sélectionné
     * * @param id L'identifiant de l'article sélectionné
     * 
     * @return Les informations de l'article
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDetailDTO> getArticleById(@PathVariable("id") final Long id) {
        Article article = this.articleService.findById(id);
        ArticleDetailDTO dto = this.articleDetailMapper.toDto(article);
        dto.setCommentaires(this.commentService.getComments(id));
        return ResponseEntity.ok(dto);
    }

    /**
     * Créer un nouvel article dans la base de données en utilisant les informations
     * reçues
     * * @param articleCreateDTO Les informations reçues sur le nouvel article
     * * @param authentication Token de l'utilisateur actuellement authentifié
     * 
     * @return Un message de confirmation
     */
    @PostMapping
    public ResponseEntity<Map<String, String>> createArticle(@RequestBody @Valid ArticleCreateDTO articleCreateDTO,
            Authentication authentication) {
        String username = authentication.getName();
        this.articleService.create(username, articleCreateDTO);
        return ResponseEntity.ok(Map.of("message", "Article créé"));
    }

    /**
     * Créer un nouveau commentaire dans la base de données en utilisant les
     * informations reçues
     * * @param id L'identifiant de l'article auquel est destiné le commentaire
     * * @param dto Le contenu texte du nouveau commentaire
     * * @param authentication Token de l'utilisateur actuellement authentifié
     * 
     * @return Un message de confirmation
     */
    @PostMapping("/{id}/commentaires")
    public ResponseEntity<Map<String, String>> createMessage(@PathVariable("id") final Long id,
            @RequestBody @Valid CommentCreateDTO dto, Authentication authentication) {
        User user = this.userService.findByUsername(authentication.getName());
        this.commentService.create(dto, user, id);
        return ResponseEntity.ok(Map.of("message", "Commentaire envoyé"));
    }
}
