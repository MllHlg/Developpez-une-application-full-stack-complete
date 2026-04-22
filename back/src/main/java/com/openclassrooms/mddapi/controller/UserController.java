package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.mapper.UserDetailMapper;
import com.openclassrooms.mddapi.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.AuthResponseDTO;
import com.openclassrooms.mddapi.dto.UserDetailDTO;
import com.openclassrooms.mddapi.dto.UserUpdateDTO;
import com.openclassrooms.mddapi.model.User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserDetailMapper userDetailMapper;
    private final IUserService userService;

    UserController(IUserService userService, UserDetailMapper userDetailMapper) {
        this.userService = userService;
        this.userDetailMapper = userDetailMapper;
    }

    @GetMapping("")
    public ResponseEntity<UserDetailDTO> getUserDetails(Authentication authentication) {
        User user = this.userService.findByUsername(authentication.getName());
        UserDetailDTO userDetailDTO = this.userDetailMapper.toDto(user);
        return ResponseEntity.ok(userDetailDTO);
    }
    
    @PutMapping("")
    public ResponseEntity<AuthResponseDTO> updateUser(@RequestBody UserUpdateDTO userDTO, Authentication authentication) {
        User user = this.userService.findByUsername(authentication.getName());
        String token = this.userService.update(user.getId(), userDTO);
        AuthResponseDTO response = new AuthResponseDTO(token, user.getId(), userDTO.getUsername(), userDTO.getEmail());
        return ResponseEntity.ok(response);
    }
}