package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.configuration.service.JWTService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.mddapi.dto.UserCreateDTO;
import com.openclassrooms.mddapi.dto.UserUpdateDTO;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.mapper.UserCreateMapper;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class UserService implements IUserService, UserDetailsService {

    private final JWTService JWTService;
    private final ThemeService themeService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserCreateMapper userCreateMapper;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
            UserCreateMapper userCreateMapper, ThemeService themeService, JWTService JWTService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userCreateMapper = userCreateMapper;
        this.themeService = themeService;
        this.JWTService = JWTService;
    }

    public void createUser(UserCreateDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cette adresse email est déjà attribuée");
        }

        if (userRepository.existsByUsername(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ce nom d'utilisateur est déjà attribué");
        }

        User user = this.userCreateMapper.toEntity(dto);
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }

    public User findByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                "Le nom d'utilisateur " + username + " ne correspond à aucun utilisateur"));
        return user;
    }

    public User findById(Long id) {
        User user = this.userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("Utilisateur non trouvé");
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String identifiant) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsernameOrEmail(identifiant, identifiant)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "L'identifiant " + identifiant + " ne correspond à aucun utilisateur"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.emptyList());
    }

    public void abonnement(Long theme_id, Long user_id) throws BadRequestException {
        Theme theme = this.themeService.findById(theme_id);
        User user = this.findById(user_id);

        boolean alreadySub = user.getThemes().stream().anyMatch(o -> o.getId().equals(theme_id));
        if (alreadySub) {
            throw new BadRequestException();
        }

        user.getThemes().add(theme);
        this.userRepository.save(user);
    }

    public void desabonnement(Long theme_id, Long user_id) throws BadRequestException {
        User user = this.findById(user_id);

        boolean alreadySub = user.getThemes().stream().anyMatch(o -> o.getId().equals(theme_id));
        if (!alreadySub) {
            throw new BadRequestException();
        }

        user.setThemes(user.getThemes().stream().filter(theme -> !theme.getId().equals(theme_id))
                .collect(Collectors.toList()));
        this.userRepository.save(user);
    }

    public String update(Long id, UserUpdateDTO dto) {
        User user = this.findById(id);
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(dto.getPassword());
        }
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        this.userRepository.save(user);

        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(user.getUsername(), null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return this.JWTService.generateToken(newAuth);
    }
}
