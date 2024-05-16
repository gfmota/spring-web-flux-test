package com.example.demo;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Flux;

import java.util.List;

public interface PokeApiClient {

    @GetExchange("pokemon/{id}")
    public Flux<Pokemon> getPokemonList(@PathVariable int id);
}
