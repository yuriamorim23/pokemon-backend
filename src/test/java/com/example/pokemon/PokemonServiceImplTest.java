package com.example.pokemon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.pokemon.dto.GuessResponseDTO;
import com.example.pokemon.dto.PokemonResponseDTO;
import com.example.pokemon.integration.PokeApiIntegrationService;
import com.example.pokemon.service.impl.PokemonServiceImpl;

@ExtendWith(MockitoExtension.class)
class PokemonServiceImplTest {

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Mock
    private PokeApiIntegrationService pokeApiIntegrationService;

    @Test
    void fetchPokemonById_shouldReturnPokemonResponseDTO() {
        // Mock response from integration service
        PokemonResponseDTO mockResponse = new PokemonResponseDTO();
        mockResponse.setId(10L);
        mockResponse.setName("caterpie");
        mockResponse.setImageUrl("https://pokeapi.co/sprites/caterpie.png");

        when(pokeApiIntegrationService.fetchPokemon(10L)).thenReturn(mockResponse);

        // Call method
        PokemonResponseDTO response = pokemonService.getPokemonById(10L);

        // Assertions
        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("caterpie", response.getName());
        assertEquals("https://pokeapi.co/sprites/caterpie.png", response.getImageUrl());
    }

    @Test
    void verifyGuess_shouldReturnCorrectResponse() {
        // Mock response from integration service
        GuessResponseDTO mockResponse = new GuessResponseDTO();
        mockResponse.setTrueName("pikachu");
        mockResponse.setCorrect(true);
        mockResponse.setFullImageUrl("https://pokeapi.co/sprites/pikachu.png");

        when(pokeApiIntegrationService.verifyGuess(10L, "pikachu")).thenReturn(mockResponse);

        // Call method
        GuessResponseDTO response = pokemonService.verifyGuess(10L, "pikachu");

        // Assertions
        assertNotNull(response);
        assertTrue(response.isCorrect());
        assertEquals("pikachu", response.getTrueName());
        assertEquals("https://pokeapi.co/sprites/pikachu.png", response.getFullImageUrl());
    }

    @Test
    void verifyGuess_shouldReturnFalseForIncorrectGuess() {
        // Mock response from integration service
        GuessResponseDTO mockResponse = new GuessResponseDTO();
        mockResponse.setTrueName("pikachu");
        mockResponse.setCorrect(false);
        mockResponse.setFullImageUrl("https://pokeapi.co/sprites/pikachu.png");

        when(pokeApiIntegrationService.verifyGuess(10L, "bulbasaur")).thenReturn(mockResponse);

        // Call method
        GuessResponseDTO response = pokemonService.verifyGuess(10L, "bulbasaur");

        // Assertions
        assertNotNull(response);
        assertFalse(response.isCorrect());
        assertEquals("pikachu", response.getTrueName());
        assertEquals("https://pokeapi.co/sprites/pikachu.png", response.getFullImageUrl());
    }
}
