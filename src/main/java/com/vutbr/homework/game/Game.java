package com.vutbr.homework.game;

import com.vutbr.homework.io.TXT;

import java.util.Scanner;

public class Game {
    private Graph graph;

    private void startGame() {
        TXT txt = new TXT();
        RandomizeItems randomizeItems = new RandomizeItems();
        String dir = txt.createDir();
        randomizeItems.randomizeItems(dir);
        graph = new Graph(dir);
        graph.generateMap();
    }

    private void gameCycle() {
        Scanner sc = new Scanner(System.in);
        System.out.println(graph.getIntro());
        sc.nextLine();
        graph.clearConsole();
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
