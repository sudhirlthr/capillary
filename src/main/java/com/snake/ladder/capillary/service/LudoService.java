package com.snake.ladder.capillary.service;

public interface LudoService {
    String initializeLudo(String str);
    Integer play(Integer diceCount);

    String stop();
}
