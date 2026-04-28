package com.openclassrooms.mddapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.openclassrooms.mddapi.dto.UserCreateDTO;
import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.mapper.UserCreateMapper;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.JWTService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserCreateMapper userCreateMapper;

    @Mock
    private IThemeService themeService;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private UserService userService;

    private UserCreateDTO dto;
    private User userEntity;

    @BeforeEach
    void setUp() {
        dto = new UserCreateDTO();
        dto.setUsername("Test");
        dto.setEmail("test@test.com");
        dto.setPassword("TEST_password_123");

        userEntity = new User();
        userEntity.setId(1L);
        userEntity.setUsername("Test");
        userEntity.setEmail("test@test.com");
        userEntity.setPassword("TEST_password_123");
        userEntity.setThemes(new ArrayList<>());
    }

    @Test
    void createUser_ShouldSaveUser_WhenDataIsntTaken() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userCreateMapper.toEntity(any(UserCreateDTO.class))).thenReturn(userEntity);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");

        userService.createUser(dto);

        verify(userRepository, times(1)).save(userEntity);
        assertEquals("encodedPassword", userEntity.getPassword());
    }

    @Test
    void createUser_ShouldThrowBadRequestException_WhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.createUser(dto);
        });

        assertEquals("Cette adresse email est déjà attribuée", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void abonnement_ShouldAddThemeToUser_WhenNotAlreadySubscribed() {
        Theme theme = new Theme();
        theme.setId(10L);
        when(themeService.findById(10L)).thenReturn(theme);
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));

        userService.abonnement(10L, 1L);

        assertTrue(userEntity.getThemes().contains(theme));
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void abonnement_ShouldThrowException_WhenAlreadySubscribed() {
        Theme theme = new Theme();
        theme.setId(10L);
        userEntity.getThemes().add(theme);

        when(themeService.findById(10L)).thenReturn(theme);
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.abonnement(10L, 1L);
        });

        assertEquals("Vous êtes déjà abonné à ce thème", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findByUsername_ShouldReturnUser() {
        when(userRepository.findByUsername("Test")).thenReturn(java.util.Optional.of(userEntity));
        User result = userService.findByUsername("Test");
        assertEquals("Test", result.getUsername());
    }

    @Test
    void findById_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));
        User result = userService.findById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void findByUsernameOrEmail_ShouldReturnUser() {
        when(userRepository.findByUsernameOrEmail("test@test.com", "test@test.com"))
                .thenReturn(java.util.Optional.of(userEntity));
        User result = userService.findByUsernameOrEmail("test@test.com");
        assertEquals("test@test.com", result.getEmail());
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails() {
        when(userRepository.findByUsernameOrEmail("Test", "Test"))
                .thenReturn(java.util.Optional.of(userEntity));

        org.springframework.security.core.userdetails.UserDetails result = userService.loadUserByUsername("Test");

        assertEquals("Test", result.getUsername());
    }

    @Test
    void desabonnement_ShouldRemoveThemeFromUser() {
        Theme theme = new Theme();
        theme.setId(10L);
        userEntity.getThemes().add(theme);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));

        userService.desabonnement(10L, 1L);

        assertFalse(userEntity.getThemes().contains(theme));
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void desabonnement_ShouldThrowException_WhenNotSubscribed() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.desabonnement(10L, 1L);
        });

        assertEquals("Vous n'êtes pas abonné à ce thème", exception.getMessage());
    }

    @Test
    void update_ShouldUpdateUserAndReturnToken() {
        com.openclassrooms.mddapi.dto.UserUpdateDTO updateDTO = new com.openclassrooms.mddapi.dto.UserUpdateDTO();
        updateDTO.setUsername("Test_2");
        updateDTO.setEmail("test_2@test.com");
        updateDTO.setPassword("TEST_2_password_123");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));
        when(userRepository.existsByEmail("test_2@test.com")).thenReturn(false);
        when(userRepository.existsByUsername("Test_2")).thenReturn(false);
        when(bCryptPasswordEncoder.encode("TEST_2_password_123")).thenReturn("encodedPassword");
        when(jwtService.generateToken(any())).thenReturn("token-jwt");

        String token = userService.update(1L, updateDTO);

        assertEquals("token-jwt", token);
        assertEquals("Test_2", userEntity.getUsername());
        assertEquals("test_2@test.com", userEntity.getEmail());
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void createUser_ShouldThrowBadRequestException_WhenUsernameAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.createUser(dto);
        });

        assertEquals("Ce nom d'utilisateur est déjà attribué", exception.getMessage());
    }

    @Test
    void update_ShouldThrowException_WhenEmailAlreadyExists() {
        com.openclassrooms.mddapi.dto.UserUpdateDTO updateDTO = new com.openclassrooms.mddapi.dto.UserUpdateDTO();
        updateDTO.setEmail("test_2@test.com"); 
        updateDTO.setUsername("Test");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));
        org.mockito.Mockito.lenient().when(userRepository.existsByEmail("test_2@test.com")).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.update(1L, updateDTO);
        });

        assertEquals("Cette adresse email est déjà attribuée", exception.getMessage());
    }

    @Test
    void update_ShouldThrowException_WhenUsernameAlreadyExists() {
        com.openclassrooms.mddapi.dto.UserUpdateDTO updateDTO = new com.openclassrooms.mddapi.dto.UserUpdateDTO();
        updateDTO.setEmail("test@test.com");
        updateDTO.setUsername("Test_2"); 

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));
        org.mockito.Mockito.lenient().when(userRepository.existsByUsername("Test_2")).thenReturn(true); 

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.update(1L, updateDTO);
        });

        assertEquals("Ce nom d'utilisateur est déjà attribué", exception.getMessage());
    }

    @Test
    void update_ShouldUpdateUser_WhenPasswordIsBlank() {
        com.openclassrooms.mddapi.dto.UserUpdateDTO updateDTO = new com.openclassrooms.mddapi.dto.UserUpdateDTO();
        updateDTO.setUsername("Test_2");
        updateDTO.setEmail("test_2@test.com");
        updateDTO.setPassword("");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userEntity));
        when(userRepository.existsByEmail("test_2@test.com")).thenReturn(false);
        when(userRepository.existsByUsername("Test_2")).thenReturn(false);

        when(jwtService.generateToken(any())).thenReturn("token");

        userService.update(1L, updateDTO);

        assertEquals("TEST_password_123", userEntity.getPassword());
    }
}