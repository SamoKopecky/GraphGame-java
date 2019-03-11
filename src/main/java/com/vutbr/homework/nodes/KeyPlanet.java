package com.vutbr.homework.nodes;

import com.vutbr.homework.game.Player;

public class KeyPlanet extends Planet {

    public KeyPlanet(String name, int id, String planetDesc, String eventDesc) {
        super(name, id, planetDesc, eventDesc);
    }

    @Override
    public boolean event(Player player) {
        player.printStatus();
        System.out.println(eventDesc);
        if (!this.isVisitedEvent()) {
            this.visitedEvent = true;
            System.out.println("Prisiel si na planetu z klucom a ziskal si kluc !");
            player.incrementNumOfKeys();
        } else {
            System.out.println("Kluc na tejto planete si uz zobral");
        }
        return false;
    }

}
