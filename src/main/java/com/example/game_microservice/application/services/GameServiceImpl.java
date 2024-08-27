package com.example.game_microservice.application.services;

import com.example.game_microservice.application.ports.in.GameService;
import com.example.game_microservice.domain.model.Game;
import com.example.game_microservice.infrastructure.adapters.out.mongodb.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public Mono<Game> createGame(Game game) {
        return Mono.just(game)
                .flatMap(gameRepository::save)
                .doOnSuccess(savedGame -> {
                    log.info("Saved game: {}", savedGame);
                    kafkaTemplate.send("games-topic", String.valueOf(savedGame));
                });
    }

    @Override
    public Flux<Game> getAllGames() {
        return gameRepository.findAll();
    }
}
