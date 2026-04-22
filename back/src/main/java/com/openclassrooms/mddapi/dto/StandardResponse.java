package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class StandardResponse {
    private String message;

    public StandardResponse(String message) {
        this.message = message;
    }
}
