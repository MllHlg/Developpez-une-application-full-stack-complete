package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.ArticleDetailDTO;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.service.ArticleService;
import com.openclassrooms.mddapi.service.CommentService;

@Component
@Mapper(componentModel = "spring", uses = { ArticleService.class, CommentService.class })
public abstract class ArticleDetailMapper  implements EntityMapper<ArticleDetailDTO, Article> {
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;

    @Mappings({
        @Mapping(target = "auteur", ignore = true),
        @Mapping(target = "theme", ignore = true)
    })
    public abstract Article toEntity(ArticleDetailDTO articleDTO);

    @Mappings({
        @Mapping(target = "auteur", source = "auteur.username"),
        @Mapping(target = "theme", source = "theme.titre"),
        @Mapping(target = "commentaires", ignore = true)
    })
    public abstract ArticleDetailDTO toDto(Article article);
}
