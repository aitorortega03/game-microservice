package com.example.game_microservice.application.services;

import com.example.game_microservice.application.ports.in.GameService;
import com.example.game_microservice.domain.model.Game;
import com.example.game_microservice.infrastructure.adapters.out.kafka.KafkaProducer;
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
    private final KafkaProducer kafkaProducer;

    @Override
    public Mono<Game> createGame(Game game) {
        return Mono.just(game)
                .flatMap(gameRepository::save)
                .doOnSuccess(savedGame -> {
                    log.info("Saved game: {}", savedGame);
                    kafkaProducer.sendMessage("New game created: " + savedGame.getName());
                })
                .doOnSuccess(g -> log.info("Game sent to topic"));
    }

    @Override
    public Flux<Game> getAllGames() {
        return gameRepository.findAll();
    }
}
