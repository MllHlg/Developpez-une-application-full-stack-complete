package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentCreateDTO {
    @NotNull
    @Size(max = 2500)
	private String comment;
}
