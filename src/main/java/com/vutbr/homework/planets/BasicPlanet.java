package com.vutbr.homework.planets;

import com.vutbr.homework.game.Player;

public class BasicPlanet extends Planet {
    private PlanetType type;

    public BasicPlanet(String name, int id, String planetDesc, String eventDesc, PlanetType type) {
        super(name, id, planetDesc, eventDesc);
        this.type = type;
    }

    @Override
    public boolean event(Player player) {
        player.appendMineralsStorage(type.getMinerals());
        player.deductHull(type.getHull());

        System.out.println(player);
        System.out.println(eventDesc);
        System.out.println("Ziskal si " + type.getMinerals() + " mineralov a startil si " + type.getHull() + "% trupu");
        
        this.visitedEvent = true;
        return false;
    }
}
