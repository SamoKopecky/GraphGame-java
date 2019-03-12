package com.vutbr.homework.nodes;

import com.vutbr.homework.game.Player;

public class BasicPlanet extends Planet {
    private PlanetTypes type;

    public BasicPlanet(String name, int id, String planetDesc, String eventDesc, PlanetTypes type) {
        super(name, id, planetDesc, eventDesc);
        this.type = type;
    }

    @Override
    public boolean event(Player player) {
        player.appendOreStorage(type.getMinerals());
        player.deductHull(type.getHull());
        player.printStatus();
        System.out.println(eventDesc);
        System.out.println("Ziskal si " + type.getMinerals() + " mineralov a startil si " + type.getHull() + "% trupu");
        this.visitedEvent = true;
        return false;
    }
}
