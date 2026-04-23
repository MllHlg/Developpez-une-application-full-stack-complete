package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ThemeDTO {
	private Long id;
	
	@NotBlank
	@Size(max = 50)
	private String titre;

	@NotNull
    @Size(max = 2500)
	private String description;

    @NotEmpty
    private boolean abonnement;
}
