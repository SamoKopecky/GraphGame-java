package com.vutbr.homework.game;

public class Game {
    private GameMap map = new GameMap();

    private void startGame() throws Exception {
        map = new GameMap();
        map.generateMap();
    }

    private void gameCycle() {
        while (!map.isGameFinished()) {
            map.chooseWhatToDo();
        }
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.startGame();

        game.gameCycle();
    }
}
