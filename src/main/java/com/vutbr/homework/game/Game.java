package com.vutbr.homework.game;

public class Game {
    private Graph grap;

    private void startGame() throws Exception {
        RandomizeItems.randomizeItems();
        grap = new Graph();
        grap.generateMap();
    }

    private void gameCycle() {
        while (!grap.isGameFinished()) {
            grap.nextChoice();
        }
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.startGame();
        game.gameCycle();
    }
}
