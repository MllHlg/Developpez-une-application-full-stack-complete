package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class ThemeDTO {
	private Long id;
	private String titre;
	private String description;
    private boolean abonnement;
}
