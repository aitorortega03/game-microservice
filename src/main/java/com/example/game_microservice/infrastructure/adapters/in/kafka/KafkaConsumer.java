package com.example.game_microservice.infrastructure.adapters.in.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "gamesTopic", groupId = "games-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}

