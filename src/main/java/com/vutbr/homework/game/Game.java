package com.vutbr.homework.game;

public class Game {

    private GameMap map = new GameMap();

    private void startGame() {
        map = new GameMap();
        map.generateMap();
    }

    private void gameCycle() {
        while (true) {
            map.printNeighbours();
            map.getNextNode();
        }
    }

    public static void main(String args[]) {
        Game game = new Game();
        game.startGame();
        game.gameCycle();
    }
}
