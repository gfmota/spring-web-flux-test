package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController("/")
public class Controller {

    private WebClient webClient = WebClient.builder().baseUrl("https://pokeapi.co/api/v2").build();

//    @Autowired
//    private PokeApiClient pokeApiClient;

    @GetMapping("/")
    public ResponseEntity<?> getPokemonList() {
        List<Flux<Pokemon>> requests = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            requests.add(requestPokemon(i));
        }
//        requestPokemon(2).subscribe(res -> {
//            System.out.println(res.toString());
//        });

        List<Pokemon> response = new ArrayList<>();
        final var flux = Flux.merge(requests).onErrorComplete(error -> {
            System.out.println(error.toString());
            return false;
        });
        flux.subscribe(res -> {
            System.out.println(Thread.currentThread().getName() + res.toString());
            response.add(res);
        }, error -> System.out.println(error.toString()));
        flux.blockLast();

        return ResponseEntity.ok(response);
    }

    private Flux<Pokemon> requestPokemon(final int id){
        try {
            return webClient.get()
                    .uri("/pokemon/" + id)
                    .retrieve()
                    .bodyToFlux(Pokemon.class);
        } catch (WebClientResponseException e) {
            return Flux.just(new Pokemon());
        }
    }
}
