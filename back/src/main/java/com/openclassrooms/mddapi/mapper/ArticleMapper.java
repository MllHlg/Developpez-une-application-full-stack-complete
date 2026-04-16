package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.model.Article;

@Component
@Mapper(componentModel = "spring")
public abstract class ArticleMapper implements EntityMapper<ArticleDTO, Article> {
    
    @Mappings({
        @Mapping(target = "auteur", ignore = true),
        @Mapping(target = "theme", ignore = true)
    })
    public abstract Article toEntity(ArticleDTO articleDTO);

    @Mappings({
        @Mapping(target = "auteur", source = "auteur.username")
    })
    public abstract ArticleDTO toDto(Article article);
}