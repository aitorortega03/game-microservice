package com.example.game_microservice.application.services;

import com.example.game_microservice.application.ports.in.GameService;
import com.example.game_microservice.domain.model.Game;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameServiceImpl implements GameService {
    @Override
    public Mono<Game> createGame(Game game) {
        return null;
    }

    @Override
    public Flux<Game> getAllGames() {
        return null;
    }
}
