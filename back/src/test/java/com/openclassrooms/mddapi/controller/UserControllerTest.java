package com.openclassrooms.mddapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.security.core.Authentication; // ✅ Remplacement de Principal par Authentication

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.mddapi.dto.UserDetailDTO;
import com.openclassrooms.mddapi.dto.UserUpdateDTO;
import com.openclassrooms.mddapi.mapper.UserDetailMapper;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.IUserService;

@WebMvcTest(
    controllers = UserController.class,
    excludeAutoConfiguration = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
    }
)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IUserService userService;

    @MockitoBean
    private UserDetailMapper userDetailMapper;

    @MockitoBean
    private JpaMetamodelMappingContext jpaMappingContext;

    @Test
    public void testGetUserDetails_shouldReturnUser() throws Exception {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("Test");
        mockUser.setEmail("test@test.com");

        UserDetailDTO mockDto = new UserDetailDTO();
        mockDto.setId(1L);
        mockDto.setUsername("Test");
        mockDto.setEmail("test@test.com");

        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.getName()).thenReturn("test@test.com");
        
        when(userService.findByUsername("test@test.com")).thenReturn(mockUser);
        when(userDetailMapper.toDto(mockUser)).thenReturn(mockDto);

        mockMvc.perform(get("/api/user")
                .principal(mockAuth))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.username").value("Test"))
               .andExpect(jsonPath("$.email").value("test@test.com"));
    }

    @Test
    public void testUpdateUser_shouldReturnAuthResponse() throws Exception {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("Test");
        mockUser.setEmail("test@test.com");

        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.getName()).thenReturn("test@test.com");
        when(userService.findByUsername("test@test.com")).thenReturn(mockUser);
        when(userService.update(anyLong(), any(UserUpdateDTO.class))).thenReturn("token-jwt");

        String updateRequest = "{"
                + "\"username\": \"Test_2\","
                + "\"email\": \"test_2@test.com\","
                + "\"password\": \"TEST_password_123\""
                + "}";

        mockMvc.perform(put("/api/user")
                .principal(mockAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequest))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.token").value("token-jwt"))
               .andExpect(jsonPath("$.username").value("Test_2"))
               .andExpect(jsonPath("$.id").value(1L));
    }
}