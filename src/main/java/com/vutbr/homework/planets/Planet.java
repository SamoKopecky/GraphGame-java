package com.vutbr.homework.planets;

import com.vutbr.homework.game.Player;
import com.vutbr.homework.paths.Path;

import java.util.HashMap;
import java.util.Map;

public abstract class Planet {
    private String name;
    private int id;
    private Map<Planet, Path> neighbours;
    private String planetDesc;
    String eventDesc;
    boolean visitedEvent = false;

    Planet(String name, int id, String planetDesc, String eventDesc) {
        this.eventDesc = eventDesc;
        this.neighbours = new HashMap<>();
        this.name = name;
        this.id = id;
        this.planetDesc = planetDesc;
    }

    public boolean isEventNotVisited() {
        return !visitedEvent;
    }

    public void setVisitedEvent(boolean visitedEvent) {
        this.visitedEvent = visitedEvent;
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

    public String getPlanetDesc() {
        return planetDesc;
    }

    public abstract boolean event(Player player);
}
