package com.vutbr.homework.game;

import com.vutbr.homework.files.TXT;

public class Game {
    private Graph graph;

    private void startGame() {
        TXT txt = new TXT();
        String dir = txt.createDir();
        RandomizeItems.randomizeItems(dir);
        graph = new Graph(dir);
        graph.generateMap();
    }

    private void gameCycle() {
        while (!graph.isGameFinished() && !graph.getPlayer().isPlayerDead()) {
            graph.nextChoice();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
        game.gameCycle();
    }
}
