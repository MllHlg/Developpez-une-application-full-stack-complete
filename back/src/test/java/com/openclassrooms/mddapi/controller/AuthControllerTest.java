package com.openclassrooms.mddapi.controller;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.security.JWTService;
import com.openclassrooms.mddapi.service.IUserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

@WebMvcTest(controllers = AuthController.class, excludeAutoConfiguration = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IUserService userService;

    @MockitoBean
    private JWTService jwtService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JpaMetamodelMappingContext jpaMappingContext;

    @Test
    public void testAuthenticateUser_shouldReturnJwtResponse() throws Exception {
        Authentication fakeAuth = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(fakeAuth);
        when(jwtService.generateToken(fakeAuth)).thenReturn("fake-token-jwt-fake-token-jwt");

        UserDetails fakeUserDetails = org.springframework.security.core.userdetails.User
                .withUsername("test@test.com")
                .password("test_password")
                .authorities(new ArrayList<>()).build();
        when(userService.loadUserByUsername("test@test.com")).thenReturn(fakeUserDetails);

        User fakeUser = new User();
        fakeUser.setId(1L);
        fakeUser.setUsername("Test");
        fakeUser.setEmail("test@test.com");
        when(userService.findByUsername("test@test.com")).thenReturn(fakeUser);

        String loginRequest = "{"
                + "\"identifiant\": \"test@test.com\","
                + "\"password\": \"TEST_password_123\""
                + "}";
        this.mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void testRegisterUser_shouldReturnSuccessMessage() throws Exception {
        String registerRequest = "{"
                + "\"username\": \"Test\","
                + "\"email\": \"test@test.com\","
                + "\"password\": \"TEST_password_123\""
                + "}";

        this.mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Utilisateur créé !"));
    }

}
