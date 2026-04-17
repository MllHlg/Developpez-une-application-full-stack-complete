package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.UserCreateDTO;
import com.openclassrooms.mddapi.model.User;

@Component
@Mapper(componentModel = "spring")
public abstract class UserCreateMapper implements EntityMapper<UserCreateDTO, User> {
    
    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "themes", ignore = true)
    })
    public abstract User toEntity(UserCreateDTO userCreateDTO);
}