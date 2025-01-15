package com.example.pokemon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pokemon.dto.GuessResponseDTO;
import com.example.pokemon.dto.PokemonResponseDTO;
import com.example.pokemon.integration.PokeApiIntegrationService;
import com.example.pokemon.service.PokemonService;

@Service
public class PokemonServiceImpl implements PokemonService {

    @Autowired
    private PokeApiIntegrationService pokeApiService;

    @Override
    public PokemonResponseDTO getRandomPokemon() {
        return pokeApiService.fetchRandomPokemon();
    }

    @Override
    public GuessResponseDTO verifyGuess(Long id, String guess) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("The Pokémon ID must be greater than 0 and cannot be null.");
        }
        if (guess == null || guess.isBlank()) {
            throw new IllegalArgumentException("The Pokémon name cannot be empty.");
        }
        return pokeApiService.verifyGuess(id, guess);
    }
}



