package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.mapper.ThemeMapper;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;

@Service
public class ThemeService implements IThemeService {

	private ThemeRepository themeRepository;
	private ThemeMapper themeMapper;

	public ThemeService(ThemeRepository themeRepository, ThemeMapper themeMapper) {
		this.themeRepository = themeRepository;
		this.themeMapper = themeMapper;
	}

	/**
	 * Récupère la liste des thèmes
	 * * @param user L'utilisateur actuellement authentifié
	 * 
	 * @return Une liste de thèmes avec le statut de l'abonnement de l'utilisateur
	 *         connecté
	 */
	public List<ThemeDTO> getThemes(User user) {
		List<Theme> themes = this.themeRepository.findAll();
		List<ThemeDTO> themesDTO = this.themeMapper.toDto(themes);
		List<Theme> abonnements = user.getThemes();

		for (ThemeDTO dto : themesDTO) {
			boolean isSubscribed = abonnements.stream()
					.anyMatch(theme -> theme.getId().equals(dto.getId()));
			dto.setAbonnement(isSubscribed);
		}
		return themesDTO;
	}

	/**
	 * Récupère le thème correspondant à l'identifiant donné
	 * * @param id L'identifiant du thème voulu
	 * 
	 * @return Le thème s'il existe et lève une exception dans le cas où il n'est
	 *         pas présent
	 */
	public Theme findById(Long id) {
		Theme theme = this.themeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Thème non trouvé"));
		return theme;
	}
}
