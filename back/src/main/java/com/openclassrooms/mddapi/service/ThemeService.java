package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.repository.ThemeRepository;

@Service
public class ThemeService implements IThemeService {

	private ThemeRepository themeRepository;
	
	public ThemeService(ThemeRepository themeRepository) {
		this.themeRepository = themeRepository;
	}

	@Override
	public List<Theme> getThemes() {
		return this.themeRepository.findAll();
	}
	
}
