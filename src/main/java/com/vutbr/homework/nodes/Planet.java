package com.vutbr.homework.nodes;

import com.vutbr.homework.game.Player;
import com.vutbr.homework.paths.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class Planet {
    private String name;
    private int id;
    private Map<Planet, Path> neighbours;

    Planet(String name, int id) {
        this.neighbours = new HashMap<>();
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Map<Planet, Path> getNeighbours() {
        return neighbours;
    }

    public void setNeighbour(Planet planet, Path path) {
        this.neighbours.put(planet, path);
    }

    public abstract boolean event(Player player);
}
