package com.openclassrooms.mddapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.service.IThemeService;

@RestController
@RequestMapping("/topic")
public class ThemeController {
	
	private IThemeService themeService;
	
	public ThemeController(IThemeService themeService) {
		this.themeService = themeService;		
	}

	@GetMapping
	public List<Theme> getTopics() {
		return themeService.getTopics();
	}
	
	
}
