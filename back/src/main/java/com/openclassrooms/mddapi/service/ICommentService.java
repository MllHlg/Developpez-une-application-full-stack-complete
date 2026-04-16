package com.openclassrooms.mddapi.service;

import java.util.List;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.model.Comment;

public interface ICommentService {
    List<CommentDTO> getComments(Long articleId);

    Comment findById(Long id);
}
