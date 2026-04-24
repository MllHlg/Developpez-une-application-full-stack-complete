package com.openclassrooms.mddapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.IThemeService;
import com.openclassrooms.mddapi.service.IUserService;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/themes")
public class ThemeController {
	
	private final IUserService userService;
    private IThemeService themeService;
	
	public ThemeController(IThemeService themeService, IUserService userService) {
		this.themeService = themeService;
        this.userService = userService;		
	}

	@GetMapping()
	public ResponseEntity<List<ThemeDTO>> getThemes(Authentication authentication) {
		User user = this.userService.findByUsername(authentication.getName());
		List<ThemeDTO> themes = this.themeService.getThemes(user);
		return ResponseEntity.ok().body(themes);
	}

	@PostMapping("/{id}/abonnement")
	public ResponseEntity<Map<String, String>> abonnement(@PathVariable("id") final long id, Authentication authentication) {
		User user = this.userService.findByUsername(authentication.getName());
		this.userService.abonnement(id, user.getId());
		return ResponseEntity.ok(Map.of("message","Abonnement confirmé"));
	}
	
	@DeleteMapping("/{id}/abonnement")
	public ResponseEntity<Map<String, String>> desabonnement(@PathVariable("id") final long id, Authentication authentication) {
		User user = this.userService.findByUsername(authentication.getName());
		this.userService.desabonnement(id, user.getId());
		return ResponseEntity.ok(Map.of("message","Désabonnement confirmé"));
	}
}
