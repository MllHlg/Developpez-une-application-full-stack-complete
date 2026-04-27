package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class CommentDTO {
	private Long id;
	private String auteur;
	private String comment;
}
