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
    public PokemonResponseDTO getPokemonById(Long id) {
        return pokeApiService.fetchPokemon(id);
    }

    @Override
    public GuessResponseDTO verifyGuess(Long id, String guess) {
        return pokeApiService.verifyGuess(id, guess);
    }
}
