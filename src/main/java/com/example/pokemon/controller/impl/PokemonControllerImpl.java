package com.example.pokemon.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pokemon.controller.PokemonController;
import com.example.pokemon.dto.GuessResponseDTO;
import com.example.pokemon.dto.PokemonResponseDTO;
import com.example.pokemon.integration.impl.PokeApiIntegrationServiceImpl;

@RestController
@RequestMapping("/api/v1/pokemon")
public class PokemonControllerImpl  implements PokemonController{

	@Autowired
	private PokeApiIntegrationServiceImpl pokeApiIntegrationServiceImpl;

    @GetMapping("/{id}")
    public ResponseEntity<PokemonResponseDTO> getPokemonById(@PathVariable Long id) {
        return ResponseEntity.ok(pokeApiIntegrationServiceImpl.fetchPokemon(id));
    }

    @PostMapping("/verify")
    public ResponseEntity<GuessResponseDTO> verifyGuess(@RequestParam Long id, @RequestParam String guess) {
        return ResponseEntity.ok(pokeApiIntegrationServiceImpl.verifyGuess(id, guess));
    }
}
