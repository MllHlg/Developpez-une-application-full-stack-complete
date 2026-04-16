package com.openclassrooms.mddapi.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleDetailDTO extends ArticleDTO {

    @NotBlank
    @Size(max = 50)
    private String theme;

    private List<CommentDTO> commentaires;
    
}
