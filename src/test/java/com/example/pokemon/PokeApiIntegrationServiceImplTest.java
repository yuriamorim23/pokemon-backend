package com.example.pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.pokemon.dto.GuessResponseDTO;
import com.example.pokemon.dto.PokemonResponseDTO;
import com.example.pokemon.integration.impl.PokeApiIntegrationServiceImpl;

class PokeApiIntegrationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokeApiIntegrationServiceImpl pokeApiIntegrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchPokemon_Success() {
        Long pokemonId = 1L;
        String pokemonName = "bulbasaur";
        String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png";

        // Mock API response
        Map<String, Object> apiResponse = new HashMap<>();
        apiResponse.put("name", pokemonName);
        Map<String, Object> sprites = new HashMap<>();
        sprites.put("front_default", imageUrl);
        apiResponse.put("sprites", sprites);

        ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {};
        when(restTemplate.exchange(anyString(), eq(org.springframework.http.HttpMethod.GET), isNull(), eq(responseType)))
                .thenReturn(ResponseEntity.ok(apiResponse));

        // Call the service method
        PokemonResponseDTO response = pokeApiIntegrationService.fetchPokemon(pokemonId);

        // Assertions
        assertNotNull(response);
        assertEquals(pokemonId, response.getId());
        assertEquals(pokemonName, response.getName());
        assertNotNull(response.getImageUrl());
        assertEquals(3, response.getOptions().size());
    }

    @Test
    void testFetchPokemon_InvalidId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> pokeApiIntegrationService.fetchPokemon(-1L));
    }

    @Test
    void testVerifyGuess_CorrectGuess() {
        Long pokemonId = 25L;
        String correctName = "pikachu";
        String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png";

        Map<String, Object> apiResponse = new HashMap<>();
        apiResponse.put("name", correctName);
        Map<String, Object> sprites = new HashMap<>();
        sprites.put("front_default", imageUrl);
        apiResponse.put("sprites", sprites);

        ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {};
        when(restTemplate.exchange(anyString(), eq(org.springframework.http.HttpMethod.GET), isNull(), eq(responseType)))
                .thenReturn(ResponseEntity.ok(apiResponse));

        GuessResponseDTO response = pokeApiIntegrationService.verifyGuess(pokemonId, "Pikachu");

        assertNotNull(response);
        assertTrue(response.isCorrect());
        assertEquals(correctName, response.getTrueName());
        assertEquals(imageUrl, response.getFullImageUrl());
    }

    @Test
    void testVerifyGuess_WrongGuess() {
        Long pokemonId = 150L;
        String correctName = "mewtwo";
        String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/150.png";

        Map<String, Object> apiResponse = new HashMap<>();
        apiResponse.put("name", correctName);
        Map<String, Object> sprites = new HashMap<>();
        sprites.put("front_default", imageUrl);
        apiResponse.put("sprites", sprites);

        ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {};
        when(restTemplate.exchange(anyString(), eq(org.springframework.http.HttpMethod.GET), isNull(), eq(responseType)))
                .thenReturn(ResponseEntity.ok(apiResponse));

        GuessResponseDTO response = pokeApiIntegrationService.verifyGuess(pokemonId, "Charizard");

        assertNotNull(response);
        assertFalse(response.isCorrect());
        assertEquals(correctName, response.getTrueName());
    }
}



