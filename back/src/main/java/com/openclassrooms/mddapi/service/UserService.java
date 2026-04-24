package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.configuration.service.JWTService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.UserCreateDTO;
import com.openclassrooms.mddapi.dto.UserUpdateDTO;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.mapper.UserCreateMapper;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class UserService implements IUserService, UserDetailsService {

    private final JWTService JWTService;
    private final IThemeService themeService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserCreateMapper userCreateMapper;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
            UserCreateMapper userCreateMapper, IThemeService themeService, JWTService JWTService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userCreateMapper = userCreateMapper;
        this.themeService = themeService;
        this.JWTService = JWTService;
    }

    public void createUser(UserCreateDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Cette adresse email est déjà attribuée");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new BadRequestException("Ce nom d'utilisateur est déjà attribué");
        }

        User user = this.userCreateMapper.toEntity(dto);
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }

    public User findByUsername(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                "Le nom d'utilisateur " + username + " ne correspond à aucun utilisateur"));
        return user;
    }

    public User findById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        return user;
    }

    public User findByUsernameOrEmail(String identifiant) {
        User user = this.userRepository.findByUsernameOrEmail(identifiant, identifiant)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "L'identifiant " + identifiant + " ne correspond à aucun utilisateur"));
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String identifiant) {
        User user = this.findByUsernameOrEmail(identifiant);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.emptyList());
    }

    public void abonnement(Long theme_id, Long user_id) {
        Theme theme = this.themeService.findById(theme_id);
        User user = this.findById(user_id);

        boolean alreadySub = user.getThemes().stream().anyMatch(o -> o.getId().equals(theme_id));
        if (alreadySub) {
            throw new BadRequestException("Vous êtes déjà abonné à ce thème");
        }

        user.getThemes().add(theme);
        this.userRepository.save(user);
    }

    public void desabonnement(Long theme_id, Long user_id) {
        User user = this.findById(user_id);

        boolean alreadySub = user.getThemes().stream().anyMatch(o -> o.getId().equals(theme_id));
        if (!alreadySub) {
            throw new BadRequestException("Vous n'êtes pas abonné à ce thème");
        }

        user.setThemes(user.getThemes().stream().filter(theme -> !theme.getId().equals(theme_id))
                .collect(Collectors.toList()));
        this.userRepository.save(user);
    }

    public String update(Long id, UserUpdateDTO dto) {
        User user = this.findById(id);
        if (!user.getEmail().equals(dto.getEmail()) && userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Cette adresse email est déjà attribuée");
        }

        if (!user.getUsername().equals(dto.getUsername()) && userRepository.existsByUsername(dto.getUsername())) {
            throw new BadRequestException("Ce nom d'utilisateur est déjà attribué");
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(this.bCryptPasswordEncoder.encode(dto.getPassword()));
        }
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        this.userRepository.save(user);

        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(user.getUsername(), null,
                new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return this.JWTService.generateToken(newAuth);
    }
}
