package com.example.game_microservice.infrastructure.adapters.out.kafka;

import com.example.game_microservice.domain.events.GameEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendGameEvent(String topic, GameEvent gameEvent) {
        try {
            String gameEventJson = objectMapper.writeValueAsString(gameEvent);
            kafkaTemplate.send(topic, gameEventJson);
        } catch (JsonProcessingException e) {
            log.error("Error serializing game event", e);
        }
    }
}
