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

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper, IArticleService articleService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.articleService = articleService;
    }

    public List<CommentDTO> getComments(Long articleId) {
        List<Comment> comments = this.commentRepository.findByArticleId(articleId);
        return this.commentMapper.toDto(comments);
    }

    public void create(CommentCreateDTO texte, User user, Long article_id) {
        Comment comment = new Comment();
        Article article = this.articleService.findById(article_id);
        comment.setArticle(article);
        comment.setAuteur(user);
        comment.setComment(texte.getComment());
        this.commentRepository.save(comment);
    }
}
