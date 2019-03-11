package com.vutbr.homework.nodes;

import com.vutbr.homework.game.Player;

public class BasicPlanet extends Planet {
    public BasicPlanet(String name, int id, String planetDesc) {
        super(name, id, planetDesc);
    }

    @Override
    public boolean event(Player player) {
        player.printStatus();
        System.out.println(eventDesc);
        System.out.println("obycajna planeta");
        this.visitedEvent = true;
        return false;
    }
}
