package com.example.pokemon.dto;

import lombok.Data;

@Data
public class GuessResponseDTO {

	private String trueName;
    private String fullImageUrl;
    private boolean isCorrect;
	
}
