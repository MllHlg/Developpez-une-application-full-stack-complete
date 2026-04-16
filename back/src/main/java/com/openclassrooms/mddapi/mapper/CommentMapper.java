package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.model.Comment;

@Component
@Mapper(componentModel = "spring")
public abstract class CommentMapper implements EntityMapper<CommentDTO, Comment> {

    @Mappings({
        @Mapping(target = "auteur", source = "auteur.username")
    })
    public abstract CommentDTO toDto(Comment comment);

    @Mappings({
        @Mapping(target = "auteur", ignore = true),
        @Mapping(target = "article", ignore = true)
    })
    public abstract Comment toEntity(CommentDTO commentDTO);
}
