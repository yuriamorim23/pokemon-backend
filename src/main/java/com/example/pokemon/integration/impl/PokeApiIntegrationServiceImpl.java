package com.example.pokemon.integration.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.pokemon.dto.GuessResponseDTO;
import com.example.pokemon.dto.PokemonResponseDTO;
import com.example.pokemon.exception.PokeApiIntegrationException;
import com.example.pokemon.integration.PokeApiIntegrationService;

@Service
public class PokeApiIntegrationServiceImpl implements PokeApiIntegrationService {

    // Base URL for the Pokémon API
    private static final String POKE_API_BASE_URL = "https://pokeapi.co/api/v2/";
    private static final String SPRITES_KEY = "sprites";
    private static final String FRONT_DEFAULT_KEY = "front_default";
    private static final String NAME_KEY = "name";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public PokemonResponseDTO fetchPokemon(Long pokemonId) {
        if (pokemonId == null || pokemonId <= 0) {
            throw new IllegalArgumentException("Invalid Pokémon ID: " + pokemonId);
        }

        String url = POKE_API_BASE_URL + "pokemon/" + pokemonId;

        try {
            // Fetch Pokémon data from the API
            Map<String, Object> pokemonData = fetchPokemonData(url);
            if (pokemonData == null) {
                throw new PokeApiIntegrationException("No response from API for Pokémon ID " + pokemonId);
            }

            // Extract necessary details from API response
            String correctName = extractPokemonName(pokemonData);
            String imageUrl = extractImageUrl(pokemonData);

            // Generate incorrect answer options
            List<String> incorrectOptions = fetchRandomPokemonNames(2, pokemonId);
            incorrectOptions.add(correctName);
            Collections.shuffle(incorrectOptions);

            // Create and return response DTO
            PokemonResponseDTO dto = new PokemonResponseDTO();
            dto.setId(pokemonId);
            dto.setName(correctName);
            dto.setImageUrl(imageUrl);
            dto.setOptions(incorrectOptions);

            return dto;
        } catch (Exception e) {
            throw new PokeApiIntegrationException("Error fetching Pokémon ID " + pokemonId, e);
        }
    }

    @Override
    public GuessResponseDTO verifyGuess(Long id, String guess) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid Pokémon ID: " + id);
        }
        if (guess == null || guess.isBlank()) {
            throw new IllegalArgumentException("The Pokémon name cannot be empty.");
        }

        String url = POKE_API_BASE_URL + "pokemon/" + id;

        try {
            // Fetch Pokémon data from the API
            Map<String, Object> pokemonData = fetchPokemonData(url);
            if (pokemonData == null) {
                throw new PokeApiIntegrationException("No response from API for Pokémon ID " + id);
            }

            // Extract correct Pokémon name and image
            String trueName = extractPokemonName(pokemonData);
            boolean isCorrect = trueName.equalsIgnoreCase(guess);
            String fullImageUrl = extractImageUrl(pokemonData);

            // Create and return response DTO
            GuessResponseDTO dto = new GuessResponseDTO();
            dto.setTrueName(trueName);
            dto.setFullImageUrl(fullImageUrl);
            dto.setCorrect(isCorrect);

            return dto;
        } catch (Exception e) {
            throw new PokeApiIntegrationException("Error verifying guess for Pokémon ID " + id, e);
        }
    }

    // Fetches Pokémon data from the external API
    private Map<String, Object> fetchPokemonData(String url) {
        ResponseEntity<Map<String, Object>> response =
            restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    // Extracts the Pokémon name from API response
    private String extractPokemonName(Map<String, Object> pokemonData) {
        Object nameObject = pokemonData.get(NAME_KEY);
        if (nameObject instanceof String name) {
            return name;
        }
        throw new PokeApiIntegrationException("Pokémon name not found in API response.");
    }

    // Extracts the Pokémon image URL from API response
    private String extractImageUrl(Map<String, Object> pokemonData) {
        Object spritesObject = pokemonData.get(SPRITES_KEY);
        if (spritesObject instanceof Map<?, ?> sprites) {
            Object frontDefault = sprites.get(FRONT_DEFAULT_KEY);
            if (frontDefault instanceof String frontImageUrl) {
                return frontImageUrl;
            }
        }
        throw new PokeApiIntegrationException("Pokémon image not found in API response.");
    }

    // Fetches random Pokémon names for incorrect options
    private List<String> fetchRandomPokemonNames(int count, Long excludeId) {
        List<String> names = new ArrayList<>();
        while (names.size() < count) {
            int randomId = (int) (Math.random() * 1000) + 1;
            if (randomId == excludeId) continue;

            String url = POKE_API_BASE_URL + "pokemon/" + randomId;
            try {
                Map<String, Object> pokemonData = fetchPokemonData(url);
                String name = extractPokemonName(pokemonData);
                if (!names.contains(name)) {
                    names.add(name);
                }
            } catch (Exception ignored) {
                // Ignore any errors and retry
            }
        }
        return names;
    }
}