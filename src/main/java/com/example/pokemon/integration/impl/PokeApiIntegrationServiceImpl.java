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

    private static final String POKE_API_BASE_URL = "https://pokeapi.co/api/v2/";
    private static final String SPRITES_KEY = "sprites";
    private static final String FRONT_DEFAULT_KEY = "front_default";
    private static final String NAME_KEY = "name";
    private static final int MAX_POKEMON_ID = 50;
    private static final int MIN_POKEMON_ID = 1;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public PokemonResponseDTO fetchRandomPokemon() {
        int randomId = (int) (Math.random() * MAX_POKEMON_ID) + MIN_POKEMON_ID;
        String url = POKE_API_BASE_URL + "pokemon/" + randomId;

        try {
            // Fetch the correct Pokémon data
            Map<String, Object> correctPokemonData = fetchPokemonData(url);

            if (correctPokemonData == null) {
                throw new PokeApiIntegrationException("Null response when fetching Pokémon with ID " + randomId);
            }

            // Extract the correct Pokémon name and image URL
            String correctName = extractPokemonName(correctPokemonData);
            String silhouetteImageUrl = extractImageUrl(correctPokemonData);

            // Generate incorrect options
            List<String> incorrectOptions = fetchRandomPokemonNames(2, randomId);

            // Combine options and shuffle
            incorrectOptions.add(correctName);
            Collections.shuffle(incorrectOptions);

            // Prepare the response DTO
            PokemonResponseDTO dto = new PokemonResponseDTO();
            dto.setId((long) randomId);
            dto.setSilhouetteImageUrl(silhouetteImageUrl);
            dto.setOptions(incorrectOptions);

            return dto;
        } catch (Exception e) {
            throw new PokeApiIntegrationException("Error while fetching Pokémon with ID " + randomId, e);
        }
    }

    @Override
    public GuessResponseDTO verifyGuess(Long id, String guess) {
        String url = POKE_API_BASE_URL + "pokemon/" + id;

        try {
            Map<String, Object> pokemonData = fetchPokemonData(url);

            if (pokemonData == null) {
                throw new PokeApiIntegrationException("Null response from API when verifying the guess.");
            }

            String trueName = extractPokemonName(pokemonData);
            boolean isCorrect = trueName.equalsIgnoreCase(guess);

            String fullImageUrl = extractImageUrl(pokemonData);

            GuessResponseDTO dto = new GuessResponseDTO();
            dto.setTrueName(trueName);
            dto.setFullImageUrl(fullImageUrl);
            dto.setCorrect(isCorrect);

            return dto;
        } catch (Exception e) {
            throw new PokeApiIntegrationException("Error while verifying the guess", e);
        }
    }

    private Map<String, Object> fetchPokemonData(String url) {
        ResponseEntity<Map<String, Object>> responseEntity =
            restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return responseEntity.getBody();
    }

    private String extractPokemonName(Map<String, Object> pokemonData) {
        Object nameObject = pokemonData.get(NAME_KEY);
        if (nameObject instanceof String name) {
            return name;
        }
        throw new PokeApiIntegrationException("Pokémon name not found in API response.");
    }

    private String extractImageUrl(Map<String, Object> pokemonData) {
        Object spritesObject = pokemonData.get(SPRITES_KEY);
        if (spritesObject instanceof Map<?, ?> sprites) {
            Object frontDefault = sprites.get(FRONT_DEFAULT_KEY);
            if (frontDefault instanceof String frontImageUrl) {
                return frontImageUrl;
            }
        }
        throw new PokeApiIntegrationException("Image not found for the Pokémon.");
    }

    private List<String> fetchRandomPokemonNames(int count, int excludeId) {
        List<String> names = new ArrayList<>();
        while (names.size() < count) {
            int randomId = (int) (Math.random() * MAX_POKEMON_ID) + MIN_POKEMON_ID;
            if (randomId == excludeId) continue;

            String url = POKE_API_BASE_URL + "pokemon/" + randomId;
            try {
                Map<String, Object> pokemonData = fetchPokemonData(url);
                String name = extractPokemonName(pokemonData);
                if (!names.contains(name)) {
                    names.add(name);
                }
            } catch (Exception ignored) {
                // Ignore errors and retry
            }
        }
        return names;
    }
}




