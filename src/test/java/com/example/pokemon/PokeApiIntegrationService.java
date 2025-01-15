package com.example.pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.pokemon.dto.GuessResponseDTO;
import com.example.pokemon.dto.PokemonResponseDTO;
import com.example.pokemon.exception.PokeApiIntegrationException;
import com.example.pokemon.integration.impl.PokeApiIntegrationServiceImpl;

class PokeApiIntegrationServiceImplTest {

    @InjectMocks
    private PokeApiIntegrationServiceImpl service;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Map<String, Object> createMockPokemonData(String name, String imageUrl) {
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("name", name);

        Map<String, String> sprites = new HashMap<>();
        sprites.put("front_default", imageUrl);
        mockResponse.put("sprites", sprites);

        return mockResponse;
    }

    @Test
    void fetchRandomPokemon_ShouldReturnPokemonResponseDTO() {
        // Arrange
        String pokemonName = "pikachu";
        String silhouetteImageUrl = "https://example.com/silhouette.png";

        Map<String, Object> mockResponse = createMockPokemonData(pokemonName, silhouetteImageUrl);

        ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {};

        when(restTemplate.exchange(
            any(String.class),
            eq(HttpMethod.GET),
            eq(null),
            eq(responseType))
        ).thenReturn(ResponseEntity.ok(mockResponse));

        // Act
        PokemonResponseDTO response = service.fetchRandomPokemon();

        // Assert
        assertNotNull(response);
        assertEquals(silhouetteImageUrl, response.getSilhouetteImageUrl());
        assertTrue(response.getOptions().contains(pokemonName));
    }

    @Test
    void verifyGuess_ShouldReturnCorrectGuessResponseDTO() {
        // Arrange
        Long pokemonId = 25L; // Pikachu ID for example
        String correctName = "pikachu";
        String fullImageUrl = "https://example.com/full.png";

        Map<String, Object> mockResponse = createMockPokemonData(correctName, fullImageUrl);

        ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {};

        when(restTemplate.exchange(
            any(String.class),
            eq(HttpMethod.GET),
            eq(null),
            eq(responseType))
        ).thenReturn(ResponseEntity.ok(mockResponse));

        // Act
        GuessResponseDTO response = service.verifyGuess(pokemonId, "pikachu");

        // Assert
        assertNotNull(response);
        assertEquals(correctName, response.getTrueName());
        assertEquals(fullImageUrl, response.getFullImageUrl());
        assertTrue(response.isCorrect());
    }

    @Test
    void verifyGuess_ShouldReturnIncorrectGuessResponseDTO() {
        // Arrange
        Long pokemonId = 25L; // Pikachu ID for example
        String correctName = "pikachu";
        String fullImageUrl = "https://example.com/full.png";

        Map<String, Object> mockResponse = createMockPokemonData(correctName, fullImageUrl);

        ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {};

        when(restTemplate.exchange(
            any(String.class),
            eq(HttpMethod.GET),
            eq(null),
            eq(responseType))
        ).thenReturn(ResponseEntity.ok(mockResponse));

        // Act
        GuessResponseDTO response = service.verifyGuess(pokemonId, "bulbasaur");

        // Assert
        assertNotNull(response);
        assertEquals(correctName, response.getTrueName());
        assertEquals(fullImageUrl, response.getFullImageUrl());
        assertFalse(response.isCorrect());
    }

    @Test
    void fetchRandomPokemon_ShouldThrowException_WhenApiReturnsNull() {
        // Arrange
        ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {};

        when(restTemplate.exchange(
            any(String.class),
            eq(HttpMethod.GET),
            eq(null),
            eq(responseType))
        ).thenReturn(ResponseEntity.ok(null));

        // Act & Assert
        assertThrows(PokeApiIntegrationException.class, () -> service.fetchRandomPokemon());
    }
}
