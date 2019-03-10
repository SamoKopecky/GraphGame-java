package com.vutbr.homework.nodes;

import com.vutbr.homework.game.Player;

public class OrdinaryPlanet extends Planet {
    public OrdinaryPlanet(String name, int id) {
        super(name, id);
    }

    @Override
    public boolean event(Player player) {
        System.out.println("obycajna planeta");
        return false;
    }
}
