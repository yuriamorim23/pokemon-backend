package com.example.pokemon.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pokemon.dto.GuessResponseDTO;
import com.example.pokemon.dto.PokemonResponseDTO;
import com.example.pokemon.service.PokemonService;

@RestController
@RequestMapping("/api/v1/pokemon")
public class PokemonControllerImpl {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping("/{id}")
    public ResponseEntity<PokemonResponseDTO> getPokemonById(@PathVariable Long id) {
        return ResponseEntity.ok(pokemonService.getPokemonById(id));
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
