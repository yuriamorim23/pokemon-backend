package com.example.pokemon.service;

import com.example.pokemon.dto.GuessResponseDTO;
import com.example.pokemon.dto.PokemonResponseDTO;

public interface PokemonService {

	PokemonResponseDTO getPokemonById(Long id);
	GuessResponseDTO verifyGuess(Long id, String guess);
	
}
