package com.vutbr.homework.nodes;

import com.vutbr.homework.game.Player;

public class KeyPlanet extends Planet {
    private boolean visited = false;

    public KeyPlanet(String name, int id) {
        super(name, id);
    }

    @Override
    public boolean event(Player player) {
        System.out.println("Prisiel si na planetu z klucom");
        return false;
    }
}
