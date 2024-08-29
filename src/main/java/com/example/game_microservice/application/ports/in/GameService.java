package com.example.game_microservice.application.ports.in;

import com.example.game_microservice.domain.model.Game;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GameService {
    Mono<Game> createGame(Game game);

    Mono<Game> updateGame(String id, Game game);

    Mono<Void> deleteGame(String id);

    Mono<Game> getGameById(String id);

    Flux<Game> getAllGames();
}
