package com.example.game_microservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    private String id;
    private String name;
    private String genre;
}
