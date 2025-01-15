package com.example.pokemon.dto;

import java.util.List;

import lombok.Data;

@Data
public class PokemonResponseDTO {
	
	private Long id;
    private String silhouetteImageUrl;
    private List<String> options;

}
