package com.openclassrooms.mddapi.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleDetailDTO extends ArticleDTO {
    private String theme;
    private List<CommentDTO> commentaires;
}
