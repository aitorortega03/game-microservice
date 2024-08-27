package com.example.game_microservice.infrastructure.adapters.in.rest;

import com.example.game_microservice.application.ports.in.GameService;
import com.example.game_microservice.domain.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping
    public Mono<ResponseEntity<Game>> createGame(@RequestBody Game game) {
        return gameService.createGame(game)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Flux<Game> getAllGames() {
        return gameService.getAllGames();
    }
}
