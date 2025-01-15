package com.example.pokemon.integration;

import com.example.pokemon.dto.GuessResponseDTO;
import com.example.pokemon.dto.PokemonResponseDTO;

public interface PokeApiIntegrationService {
	
	PokemonResponseDTO fetchRandomPokemon();
    GuessResponseDTO verifyGuess(Long id, String guess);
}
