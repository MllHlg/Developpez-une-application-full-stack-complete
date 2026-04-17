package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginDTO {
    @NotNull
    private String identifiant;

    @NotNull
    private String password;
}
