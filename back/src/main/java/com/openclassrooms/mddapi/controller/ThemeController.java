package com.openclassrooms.mddapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.service.IThemeService;

@RestController
@RequestMapping("/api/themes")
public class ThemeController {
	
	private IThemeService themeService;
	
	public ThemeController(IThemeService themeService) {
		this.themeService = themeService;		
	}

	@GetMapping()
	public ResponseEntity<List<Theme>> getThemes() {
		List<Theme> themes = this.themeService.getThemes();
		return ResponseEntity.ok().body(themes);
	}
	
}
