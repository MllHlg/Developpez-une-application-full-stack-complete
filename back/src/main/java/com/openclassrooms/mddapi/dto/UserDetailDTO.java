package com.openclassrooms.mddapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDetailDTO {
    private Long id;
    private String username;
    private String email;
    private List<ThemeDTO> themes;
}
