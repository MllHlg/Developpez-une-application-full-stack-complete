package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.CommentCreateDTO;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;

@Service
public class CommentService implements ICommentService {
    private final IArticleService articleService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper,
            IArticleService articleService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.articleService = articleService;
    }

    /**
     * Récupère la liste des commentaires liés au l'article correspondant à
     * l'identifiant donné.
     * * @param id L'identifiant de l'article
     * 
     * @return Une liste de commentaires
     */
    public List<CommentDTO> getComments(Long articleId) {
        List<Comment> comments = this.commentRepository.findByArticleId(articleId);
        return this.commentMapper.toDto(comments);
    }

    /**
     * Créer un commentaire lié à l'article sélectionné et à l'utilisateur
     * authentifié
     * * @param dto Le contenu texte du nouveau commentaire
     * * @param user L'utilisateur actuellement authentifié
     * * @param article_id L'identifiant de l'article auquel est destiné le
     * commentaire
     */
    public void create(CommentCreateDTO dto, User user, Long article_id) {
        Comment comment = new Comment();
        Article article = this.articleService.findById(article_id);
        comment.setArticle(article);
        comment.setAuteur(user);
        comment.setComment(dto.getComment());
        this.commentRepository.save(comment);
    }
}
