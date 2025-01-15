package com.example.pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

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
    void fetchRandomPokemon_shouldReturnPokemonResponseDTO() {
        // Mock response from integration service
        PokemonResponseDTO mockResponse = new PokemonResponseDTO();
        mockResponse.setId(10L);
        mockResponse.setSilhouetteImageUrl("https://pokeapi.co/sprites/pikachu.png");
        mockResponse.setOptions(List.of("pikachu", "bulbasaur", "charmander"));

        when(pokeApiIntegrationService.fetchRandomPokemon()).thenReturn(mockResponse);

        // Call method
        PokemonResponseDTO response = pokemonService.getRandomPokemon();

        // Assertions
        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals(3, response.getOptions().size());
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
    }
}
