package com.example.pokemon.dto;

import lombok.Data;
import java.util.List;

@Data
public class PokemonResponseDTO {
    
    private Long id;
    private String name;
    private String imageUrl;
    private List<String> options;
}
