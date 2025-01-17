package com.example.pokemon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.pokemon.dto.GuessResponseDTO;
import com.example.pokemon.dto.PokemonResponseDTO;

public interface PokemonController {
	
	ResponseEntity<PokemonResponseDTO> getPokemonById(@PathVariable Long id);
	ResponseEntity<GuessResponseDTO> verifyGuess(@RequestParam Long id, @RequestParam String guess);

}
