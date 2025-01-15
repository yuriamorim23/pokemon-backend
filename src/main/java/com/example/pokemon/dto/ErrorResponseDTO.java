package com.example.pokemon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {
    private int status;
    private String error;
    private String message;
}
