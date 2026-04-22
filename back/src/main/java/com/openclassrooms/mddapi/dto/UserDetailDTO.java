package com.openclassrooms.mddapi.dto;

import java.util.List;

import com.openclassrooms.mddapi.model.Theme;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDetailDTO {
    private Long id;

    @NotNull
    @Size(max = 20)
    private String username;

    @NotNull
    @Size(max = 50)
    @Email
    private String email;
    
    private List<Theme> themes;
}
