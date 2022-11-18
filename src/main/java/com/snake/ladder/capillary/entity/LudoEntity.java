package com.snake.ladder.capillary.entity;

public class LudoEntity {
    public int[][] getLudo() {
        return ludo;
    }

    public int[][] getSnake() {
        return snake;
    }

    public int[][] getLadder() {
        return ladder;
    }

    private int[][] ludo;
    // snake with higher number and lower number
    private int snake[][] = {
            {14, 8},
            {32, 2},
            {38, 22},
            {49, 19},
            {55, 35},
            {68, 45},
            {71, 44},
            {89, 56},
            {99, 9}
    };

    private int ladder[][] = {
            {7, 16},
            {11, 54},
            {37, 50},
            {41, 72},
            {57, 85},
            {62, 72},
            {79, 85},
            {81, 97}
    };
    public LudoEntity(){
        this.ludo = new int[10][10];
    }
}
