package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.service.ArticleService;

@Component
@Mapper(componentModel = "spring", uses = { ArticleService.class })
public abstract class ArticleMapper implements EntityMapper<ArticleDTO, Article> {
    @Autowired
    ArticleService articleService;

    @Mappings({
        @Mapping(target = "auteur", expression = "java(articleDTO.getId() != null ? this.articleService.findById(articleDTO.getId()).getAuteur() : null)"),
        @Mapping(target = "theme", expression = "java(articleDTO.getId() != null ? this.articleService.findById(articleDTO.getId()).getTheme() : null)")
    })
    public abstract Article toEntity(ArticleDTO articleDTO);

    @Mappings({
        @Mapping(target = "auteur", expression = "java(article.getAuteur() != null ? article.getAuteur().getUsername() : null)")
    })
    public abstract ArticleDTO toDto(Article article);
}