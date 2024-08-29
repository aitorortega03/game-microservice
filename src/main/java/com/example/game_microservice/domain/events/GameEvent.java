package com.example.game_microservice.domain.events;

import com.example.game_microservice.domain.model.Game;
import com.example.game_microservice.domain.util.EventType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GameEvent {
    private EventType eventType;
    private Game game;
}
