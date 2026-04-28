package com.openclassrooms.mddapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.mddapi.dto.CommentCreateDTO;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private IArticleService articleService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentService commentService;

    @Test
    public void testGetComments_ShouldReturnListOfComments() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setComment("Commentaire test");

        CommentDTO dto = new CommentDTO();
        dto.setId(1L);
        dto.setComment("Commentaire test");

        when(commentRepository.findByArticleId(10L)).thenReturn(Arrays.asList(comment));
        
        when(commentMapper.toDto(org.mockito.ArgumentMatchers.<List<Comment>>any())).thenReturn(Arrays.asList(dto));

        List<CommentDTO> result = commentService.getComments(10L);

        assertEquals(1, result.size());
        assertEquals("Commentaire test", result.get(0).getComment());
    }

    @Test
    public void testCreate_ShouldSaveComment() {
        CommentCreateDTO dto = new CommentCreateDTO();
        dto.setComment("Nouveau commentaire");

        User user = new User();
        user.setId(1L);
        user.setUsername("Test");

        Article article = new Article();
        article.setId(10L);

        when(articleService.findById(10L)).thenReturn(article);
        commentService.create(dto, user, 10L);

        verify(articleService, times(1)).findById(10L);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }
}