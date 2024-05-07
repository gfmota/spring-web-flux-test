package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
public class Controller {

    @Autowired
    private PokeApiClient pokeApiClient;

    @GetMapping("/")
    public ResponseEntity<List<Pokemon>> getPokemonList() {
        return ResponseEntity.ok(pokeApiClient.getPokemonList());
    }
}
