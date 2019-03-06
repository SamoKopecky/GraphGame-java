package com.vutbr.homework.nodes;

import com.vutbr.homework.paths.Path;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private String name;
    private int id;
    private Map<Node, Path> neighbours = new HashMap<>();

    public Node(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Map<Node, Path> getNeighbours() {
        return neighbours;
    }

    public void setNeighbour(Node node, Path path) {
        this.neighbours.put(node, path);
    }
}
