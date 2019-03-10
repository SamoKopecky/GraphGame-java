package com.vutbr.homework.nodes;

import com.vutbr.homework.game.Player;

public class KeyPlanet extends Planet {
    private boolean visited = false;

    public KeyPlanet(String name, int id) {
        super(name, id);
    }

    @Override
    public boolean event(Player player) {
        if (!visited) {
            visited = true;
            System.out.println("Prisiel si na planetu z klucom a ziskal si kluc !");
            player.incrementNumOfKeys();
        } else {
            System.out.println("Kluc na tejto planete si uz zobral");
        }
        return false;
    }
}
