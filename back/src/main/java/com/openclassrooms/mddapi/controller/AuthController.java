package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.configuration.service.JWTService;
import com.openclassrooms.mddapi.dto.AuthResponseDTO;
import com.openclassrooms.mddapi.dto.UserCreateDTO;
import com.openclassrooms.mddapi.dto.UserLoginDTO;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JWTService jwtService, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserLoginDTO dto) {
        Authentication authentication = this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getIdentifiant(), dto.getPassword())
        );

        String token = this.jwtService.generateToken(authentication);

        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserCreateDTO dto) {
        userService.createUser(dto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Utilisateur créé !");
        return ResponseEntity.ok(response);
    }
    
    
}
