package com.example.game_microservice.infrastructure.adapters.out.mongodb;

import com.example.game_microservice.domain.model.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends ReactiveMongoRepository<Game,String> {
}
