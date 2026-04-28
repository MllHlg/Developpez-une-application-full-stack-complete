package com.openclassrooms.mddapi.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.IThemeService;
import com.openclassrooms.mddapi.service.IUserService;

@WebMvcTest(
    controllers = ThemeController.class,
    excludeAutoConfiguration = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
    }
)
@AutoConfigureMockMvc(addFilters = false)
public class ThemeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IUserService userService;

    @MockitoBean
    private IThemeService themeService;

    @MockitoBean
    private JpaMetamodelMappingContext jpaMappingContext;

    @Test
    public void testGetThemes_shouldReturnListOfThemes() throws Exception {
        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.getName()).thenReturn("test@test.com");

        User mockUser = new User();
        mockUser.setId(1L);

        ThemeDTO theme1 = new ThemeDTO();
        theme1.setId(1L);
        theme1.setTitre("Thème 1");

        ThemeDTO theme2 = new ThemeDTO();
        theme2.setId(2L);
        theme2.setTitre("Thème 2");

        List<ThemeDTO> themes = Arrays.asList(theme1, theme2);
        when(userService.findByUsername("test@test.com")).thenReturn(mockUser);
        when(themeService.getThemes(mockUser)).thenReturn(themes);

        mockMvc.perform(get("/api/themes")
                .principal(mockAuth))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].titre").value("Thème 1"))
               .andExpect(jsonPath("$[1].titre").value("Thème 2"));
    }

    @Test
    public void testAbonnement_shouldReturnSuccessMessage() throws Exception {
        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.getName()).thenReturn("test@test.com");

        User mockUser = new User();
        mockUser.setId(1L);

        when(userService.findByUsername("test@test.com")).thenReturn(mockUser);

        mockMvc.perform(post("/api/themes/1/abonnement")
                .principal(mockAuth))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("Abonnement confirmé"));
    }

    @Test
    public void testDesabonnement_shouldReturnSuccessMessage() throws Exception {
        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.getName()).thenReturn("test@test.com");

        User mockUser = new User();
        mockUser.setId(1L);

        when(userService.findByUsername("test@test.com")).thenReturn(mockUser);

        mockMvc.perform(delete("/api/themes/1/abonnement")
                .principal(mockAuth))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("Désabonnement confirmé"));
    }
}