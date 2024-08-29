package com.example.game_microservice.application.services;

import com.example.game_microservice.application.ports.in.GameService;
import com.example.game_microservice.domain.events.GameEvent;
import com.example.game_microservice.domain.model.Game;
import com.example.game_microservice.domain.util.EventType;
import com.example.game_microservice.infrastructure.adapters.out.kafka.KafkaProducer;
import com.example.game_microservice.infrastructure.adapters.out.mongodb.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {
    public static final String GAMES_TOPIC = "games-topic";
    private final GameRepository gameRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public Mono<Game> createGame(Game game) {
        return Mono.just(game)
                .flatMap(gameRepository::save)
                .doOnSuccess(savedGame -> {
                    log.info("Saved game: {}", savedGame);
                    GameEvent gameEvent = GameEvent.builder().eventType(EventType.CREATE).game(savedGame).build();
                    kafkaProducer.sendGameEvent(GAMES_TOPIC, gameEvent);
                })
                .doOnSuccess(g -> log.info("GameCreated sent to topic"));
    }

    @Override
    public Mono<Game> updateGame(String id, Game game) {
        return gameRepository.findById(id)
                .flatMap(existingGame -> {
                    existingGame.setName(game.getName());
                    existingGame.setGenre(game.getGenre());
                    return gameRepository.save(existingGame);
                })
                .doOnSuccess(updatedGame -> {
                    GameEvent gameEvent = GameEvent.builder().eventType(EventType.UPDATE).game(updatedGame).build();
                    kafkaProducer.sendGameEvent(GAMES_TOPIC, gameEvent);
                })
                .doOnSuccess(g -> log.info("GameUpdated sent to topic: {}", g));
    }

    @Override
    public Mono<Void> deleteGame(String id) {
        return gameRepository.findById(id)
                .flatMap(game -> gameRepository.deleteById(id)
                        .thenReturn(game))
                .doOnSuccess(deletedGame -> {
                    GameEvent gameEvent = GameEvent.builder().eventType(EventType.DELETE).game(deletedGame).build();
                    kafkaProducer.sendGameEvent(GAMES_TOPIC, gameEvent);
                })
                .doOnSuccess(g -> log.info("GameDeleted sent to topic: {}", g))
                .then();
    }

    @Override
    public Mono<Game> getGameById(String id) {
        return gameRepository.findById(id);
    }

    @Override
    public Flux<Game> getAllGames() {
        return gameRepository.findAll();
    }
}
