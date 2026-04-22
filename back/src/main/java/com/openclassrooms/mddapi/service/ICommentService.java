package com.openclassrooms.mddapi.service;

import java.util.List;

import com.openclassrooms.mddapi.dto.CommentCreateDTO;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;

public interface ICommentService {
    List<CommentDTO> getComments(Long articleId);

    Comment findById(Long id);

    void create(CommentCreateDTO texte, User user, Long article_id);
}
