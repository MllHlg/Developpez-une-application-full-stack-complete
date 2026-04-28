package com.openclassrooms.mddapi.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.dto.ArticleDetailDTO;
import com.openclassrooms.mddapi.mapper.ArticleDetailMapper;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.IArticleService;
import com.openclassrooms.mddapi.service.ICommentService;
import com.openclassrooms.mddapi.service.IUserService;

@WebMvcTest(
    controllers = ArticleController.class,
    excludeAutoConfiguration = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
    }
)
@AutoConfigureMockMvc(addFilters = false)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IUserService userService;

    @MockitoBean
    private ICommentService commentService;

    @MockitoBean
    private IArticleService articleService;

    @MockitoBean
    private ArticleMapper articleMapper;

    @MockitoBean
    private ArticleDetailMapper articleDetailMapper;

    @MockitoBean
    private JpaMetamodelMappingContext jpaMappingContext;

    @Test
    public void testGetArticles_shouldReturnListOfArticles() throws Exception {
        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.getName()).thenReturn("test@test.com");

        User mockUser = new User();
        Article mockArticle = new Article();
        ArticleDTO mockArticleDTO = new ArticleDTO();
        mockArticleDTO.setId(1L);
        mockArticleDTO.setTitre("Article 1");

        when(userService.findByUsername("test@test.com")).thenReturn(mockUser);
        when(articleService.getArticles(mockUser)).thenReturn(Arrays.asList(mockArticle));
        when(articleMapper.toDto(org.mockito.ArgumentMatchers.<List<Article>>any())).thenReturn(Arrays.asList(mockArticleDTO));

        mockMvc.perform(get("/api/articles")
                .principal(mockAuth))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1))
               .andExpect(jsonPath("$[0].titre").value("Article 1"));
    }

    @Test
    public void testGetArticleById_shouldReturnArticleDetail() throws Exception {
        Article mockArticle = new Article();
        ArticleDetailDTO mockDetailDTO = new ArticleDetailDTO();
        mockDetailDTO.setId(1L);
        mockDetailDTO.setTitre("Article 1");

        when(articleService.findById(1L)).thenReturn(mockArticle);
        when(articleDetailMapper.toDto(mockArticle)).thenReturn(mockDetailDTO);
        when(commentService.getComments(1L)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/articles/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.titre").value("Article 1"));
    }

    @Test
    public void testCreateArticle_shouldReturnSuccessMessage() throws Exception {
        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.getName()).thenReturn("test@test.com");

        String articleRequest = "{"
                + "\"titre\": \"Article 1\","
                + "\"texte\": \"Contenu Article 1.\","
                + "\"theme\": 1"
                + "}";

        mockMvc.perform(post("/api/articles")
                .principal(mockAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .content(articleRequest))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("Article créé"));
    }

    @Test
    public void testCreateMessage_shouldReturnSuccessMessage() throws Exception {
        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.getName()).thenReturn("test@test.com");

        User mockUser = new User();
        when(userService.findByUsername("test@test.com")).thenReturn(mockUser);

        String commentRequest = "{"
                + "\"comment\": \"Commentaire\""
                + "}";

        mockMvc.perform(post("/api/articles/1/commentaires")
                .principal(mockAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .content(commentRequest))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("Commentaire envoyé"));
    }
}