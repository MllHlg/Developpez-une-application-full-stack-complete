package com.openclassrooms.mddapi.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticleDTO {
    private Long id;
    
    @NotBlank
    @Size(max = 50)
    private String titre;

    @NotNull
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate date;

    @NotBlank
    @Size(max = 50)
    private String auteur;

    @NotNull
    @Size(max = 2500)
    private String texte;
}