package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController("/")
public class Controller {

    private PokeApiClient pokeApiClient;

    public Controller() {
        WebClient webClient = WebClient.builder().baseUrl("https://pokeapi.co/api/v2/").clientConnector().build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        this.pokeApiClient = factory.createClient(PokeApiClient.class);
    }

    @GetMapping("/{from}/{to}")
    public ResponseEntity<?> getPokemonList(@PathVariable int from, @PathVariable int to) {
        List<Flux<Pokemon>> requests = new ArrayList<>();
        for (int i = from; i <= to; i++) {
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
            return pokeApiClient.getPokemonList(id);
        } catch (WebClientResponseException e) {
            return Flux.just(new Pokemon());
        }
    }
}
