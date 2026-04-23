package com.openclassrooms.mddapi.service;

import java.util.List;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;

public interface IThemeService {

	List<ThemeDTO> getThemes(User user);
	Theme findById(Long id);
}
