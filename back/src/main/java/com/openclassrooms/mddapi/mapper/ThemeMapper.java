package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.model.Theme;

@Component
@Mapper(componentModel = "spring")
public abstract class ThemeMapper implements EntityMapper<ThemeDTO, Theme> {
    @Mappings({
            @Mapping(target = "abonnement", ignore = true)
    })
    public abstract ThemeDTO toDto(Theme theme);
}
