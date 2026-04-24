package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.service.IUserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.configuration.service.JWTService;
import com.openclassrooms.mddapi.dto.AuthResponseDTO;
import com.openclassrooms.mddapi.dto.UserCreateDTO;
import com.openclassrooms.mddapi.dto.UserLoginDTO;
import com.openclassrooms.mddapi.model.User;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final IUserService userService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JWTService jwtService, AuthenticationManager authenticationManager, IUserService userService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid UserLoginDTO dto) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getIdentifiant(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = this.jwtService.generateToken(authentication);

        UserDetails userDetails = this.userService.loadUserByUsername(dto.getIdentifiant());
        User user = this.userService.findByUsername(userDetails.getUsername());

        AuthResponseDTO response = new AuthResponseDTO(token, user.getId(), user.getUsername(), user.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody @Valid UserCreateDTO dto) {
        this.userService.createUser(dto);
        return ResponseEntity.ok(Map.of("message", "Utilisateur créé !"));
    }

}
