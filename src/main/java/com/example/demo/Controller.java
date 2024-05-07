package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("/")
@RequiredArgsConstructor
public class Controller {
    private final PokeApiClient pokeApiClient;

    @GetMapping("/")
    public ResponseEntity<List<Pokemon>> getPokemonList() {
        final var startTime = System.currentTimeMillis();
        final var resultList = new ArrayList<Pokemon>();

        for (int i = 1; i <= 30; i++) {
            final var p = pokeApiClient.getPokemon(i);
            System.out.println(p.toString());
            resultList.add(p);
        }

        final var endTime = System.currentTimeMillis();
        System.out.println("Duration in ms: " + String.valueOf(endTime - startTime));

        return ResponseEntity.ok(resultList);
    }
}
