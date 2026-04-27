package com.openclassrooms.mddapi.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ArticleDTO {
    private Long id;
    private String titre;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate date;
    private String auteur;
    private String texte;
}