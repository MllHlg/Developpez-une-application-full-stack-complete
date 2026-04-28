package com.openclassrooms.mddapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.mapper.ThemeMapper;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;

@ExtendWith(MockitoExtension.class)
public class ThemeServiceTest {

    @Mock
    private ThemeRepository themeRepository;

    @Mock
    private ThemeMapper themeMapper;

    @InjectMocks
    private ThemeService themeService;

    @Test
    public void testGetThemes_ShouldReturnThemesWithSubscriptionStatus() {
        Theme theme1 = new Theme(); 
        theme1.setId(1L);
        Theme theme2 = new Theme(); 
        theme2.setId(2L);

        ThemeDTO dto1 = new ThemeDTO(); 
        dto1.setId(1L);
        ThemeDTO dto2 = new ThemeDTO(); 
        dto2.setId(2L);

        User user = new User();
        user.setThemes(Arrays.asList(theme1));

        when(themeRepository.findAll()).thenReturn(Arrays.asList(theme1, theme2));
        when(themeMapper.toDto(org.mockito.ArgumentMatchers.<List<Theme>>any())).thenReturn(Arrays.asList(dto1, dto2));

        List<ThemeDTO> result = themeService.getThemes(user);

        assertEquals(2, result.size());
        assertTrue(result.get(0).isAbonnement());
        assertFalse(result.get(1).isAbonnement()); 
    }

    @Test
    public void testFindById_ShouldReturnTheme() {
        Theme theme = new Theme();
        theme.setId(1L);
        when(themeRepository.findById(1L)).thenReturn(Optional.of(theme));

        Theme result = themeService.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    public void testFindById_ShouldThrowExceptionWhenNotFound() {
        when(themeRepository.findById(0L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            themeService.findById(0L);
        });
    }
}