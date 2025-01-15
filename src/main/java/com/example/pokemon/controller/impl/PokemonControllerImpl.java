package com.example.pokemon.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pokemon.controller.PokemonController;
import com.example.pokemon.dto.GuessResponseDTO;
import com.example.pokemon.dto.PokemonResponseDTO;
import com.example.pokemon.service.PokemonService;


@RestController
@RequestMapping("/api/v1/pokemon")
public class PokemonControllerImpl implements PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping("/random")
    public ResponseEntity<PokemonResponseDTO> getRandomPokemon() {
        return ResponseEntity.ok(pokemonService.getRandomPokemon());
    }

    @PostMapping("/verify")
    public ResponseEntity<GuessResponseDTO> verifyGuess(@RequestParam Long id, @RequestParam String guess) {
        return ResponseEntity.ok(pokemonService.verifyGuess(id, guess));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Controller is working!");
    }
}
