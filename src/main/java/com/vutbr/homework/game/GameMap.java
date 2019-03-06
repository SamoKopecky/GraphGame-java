package com.vutbr.homework.game;

import com.vutbr.homework.nodes.Node;
import com.vutbr.homework.paths.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameMap {
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    private Player player;
    private ArrayList<Node> listOfNodes = new ArrayList<>();
    private Node currentNode;

    public void generateMap() {
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                this.listOfNodes.add(new Node("Start", i));
            } else {
                this.listOfNodes.add(new Node("" + i + 50, i));
            }
        }

        listOfNodes.get(0).setNeighbour(listOfNodes.get(1), new Path());
        listOfNodes.get(0).setNeighbour(listOfNodes.get(5), new Path());
        listOfNodes.get(1).setNeighbour(listOfNodes.get(3), new Path());
        listOfNodes.get(3).setNeighbour(listOfNodes.get(4), new Path());
        listOfNodes.get(4).setNeighbour(listOfNodes.get(2), new Path());
        listOfNodes.get(2).setNeighbour(listOfNodes.get(0), new Path());

        this.currentNode = listOfNodes.get(0);
    }

    public void printNeighbours() {
        int i = 0;
        System.out.println("Mozes letiet na planety : ");
        for (Map.Entry<Node, Path> entry : this.currentNode.getNeighbours().entrySet()) {
            System.out.println(ALPHABET[i] + ": Na planetu " + entry.getKey().getId() +
                    " ktora je vzdialena " + entry.getValue().getLength() + " jednotiek.");
            i++;
        }
    }

    public void getNextNode() {
        Scanner sc = new Scanner(System.in);
        char nextNode;
        int i = 0;
        Map<Character, Node> choice = new HashMap<>();

        for (Node node : this.currentNode.getNeighbours().keySet()) {
            choice.put(ALPHABET[i], node);
            i++;
        }

        System.out.println("Tvoje volba je :");
        nextNode = Character.toUpperCase(sc.next().charAt(0));

        this.currentNode = choice.get(nextNode);
    }


}
