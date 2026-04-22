package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticleCreateDTO {
	@NotBlank
	@Size(max = 50)
	private String titre;

	@NotNull
	private Long theme;

	@NotNull
    @Size(max = 2500)
	private String texte;
}
