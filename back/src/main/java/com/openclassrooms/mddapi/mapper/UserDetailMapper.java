package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.UserDetailDTO;
import com.openclassrooms.mddapi.model.User;

@Component
@Mapper(componentModel = "spring", uses = {ThemeMapper.class})
public interface UserDetailMapper extends EntityMapper<UserDetailDTO, User> {
    @Mappings({
        @Mapping(target = "password", ignore = true)
    })
    public abstract User toEntity(UserDetailDTO userCreateDTO);
}
