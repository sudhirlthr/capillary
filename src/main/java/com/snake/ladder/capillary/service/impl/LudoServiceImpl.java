package com.snake.ladder.capillary.service.impl;

import com.snake.ladder.capillary.entity.LudoEntity;
import com.snake.ladder.capillary.service.LudoService;

import com.snake.ladder.capillary.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class LudoServiceImpl implements LudoService {

    public LudoEntity getLudoEntity() {
        return ludoEntity;
    }

    private LudoEntity ludoEntity;
    private Integer p1_current_Dice_position = 0;
    private Integer p2_current_Dice_position = 0;

    public Integer getCount() {
        return count;
    }

    // below variable map current person move.
    // if count value is ODD it means player 2 is done with his chance
    // and next chance will go to person 1
    // and vice-versa
    private Integer count = 0;

    @Override
    public String initializeLudo(String str) {
        if (str.equals(Constants.START)){
            initialize();
            return Constants.READY;
        }
        return Constants.NOT_READY;
    }

    @Override
    public Integer play(Integer diceCount) {
        int[][] ludo = ludoEntity.getLudo();
        int[][] ladder = ludoEntity.getLadder();
        int[][] snake = ludoEntity.getSnake();
        Integer currentPosition = 0;

        if (p1_current_Dice_position +diceCount == 100 || p2_current_Dice_position+diceCount ==100){
            count++;
            ludoEntity = null;
            return 100;
        }

        if (count%2 == 0 ){ // player 1 will play and move his dice
            p1_current_Dice_position += diceCount;
            if (p1_current_Dice_position < 100)
                p1_current_Dice_position = getDicePosition(p1_current_Dice_position, ladder, snake);
            else p1_current_Dice_position -= diceCount;
            currentPosition = p1_current_Dice_position;
        }else {
            p2_current_Dice_position += diceCount;
            if (p2_current_Dice_position < 100)
                p2_current_Dice_position = getDicePosition(p2_current_Dice_position, ladder, snake);
            else p2_current_Dice_position -= diceCount;
            currentPosition = p2_current_Dice_position;
        }
        count++;
        return currentPosition;
    }

    @Override
    public String stop() {
        ludoEntity = null;
        return Constants.STOPPED;
    }

    private Integer getDicePosition(Integer current_dice_position, int[][] ladder, int[][] snake) {
        Integer currentPositionAfterSnakeOrLadder = current_dice_position;
        //for snake
        for (int i = 0; i < snake.length; i++) {
            if (current_dice_position.intValue() == snake[i][0]){
                // snake
                currentPositionAfterSnakeOrLadder = snake[i][1];
            }
        }


        //for ladder
        for (int i = 0; i < ladder.length; i++) {
            if (current_dice_position.intValue() == ladder[i][0]){
                // snake
                currentPositionAfterSnakeOrLadder = ladder[i][1];
            }
        }
        return currentPositionAfterSnakeOrLadder;
    }

    private void initialize(){
        ludoEntity = new LudoEntity();
    }

}
