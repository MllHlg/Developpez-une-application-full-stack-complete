package com.openclassrooms.mddapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.mddapi.dto.ArticleCreateDTO;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private IUserService userService;

    @Mock
    private IThemeService themeService;

    @InjectMocks
    private ArticleService articleService;

    @Test
    public void testGetArticles_WithNoThemes_ShouldReturnEmptyList() {
        User user = new User();
        user.setThemes(new ArrayList<>());

        List<Article> result = articleService.getArticles(user);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetArticles_WithThemes_ShouldReturnArticles() {
        Theme theme = new Theme();
        theme.setId(1L);
        
        User user = new User();
        user.setThemes(Arrays.asList(theme));

        Article article = new Article();
        article.setTitre("Article 1");

        when(articleRepository.findAllByThemeIn(user.getThemes())).thenReturn(Arrays.asList(article));

        List<Article> result = articleService.getArticles(user);

        assertEquals(1, result.size());
        assertEquals("Article 1", result.get(0).getTitre());
    }

    @Test
    public void testFindById_ShouldReturnArticle() {
        Article article = new Article();
        article.setId(10L);
        
        when(articleRepository.findById(10L)).thenReturn(Optional.of(article));

        Article result = articleService.findById(10L);

        assertEquals(10L, result.getId());
    }

    @Test
    public void testFindById_WhenNotFound_ShouldThrowException() {
        when(articleRepository.findById(0L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            articleService.findById(0L);
        });
    }

    @Test
    public void testCreate_ShouldSaveArticle() {
        ArticleCreateDTO dto = new ArticleCreateDTO();
        dto.setTitre("Article 2");
        dto.setTexte("Contenu Article 2");
        dto.setTheme(1L);

        User user = new User();
        user.setUsername("Test");

        Theme theme = new Theme();
        theme.setId(1L);

        when(userService.findByUsername("Test")).thenReturn(user);
        when(themeService.findById(1L)).thenReturn(theme);

        articleService.create("Test", dto);

        verify(articleRepository, times(1)).save(any(Article.class));
    }
}