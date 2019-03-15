package com.vutbr.homework.game;

public class Game {
    private Graph graph;

    private void startGame() throws Exception {
        Graph.printInto();
        RandomizeItems.randomizeItems();
        graph = new Graph();
        graph.generateMap();
    }

    private void gameCycle() {
        while (!graph.isGameFinished() && !graph.getPlayer().isPlayerDead()) {
            graph.nextChoice();
        }
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.startGame();
        game.gameCycle();
    }
}
