package com.vutbr.homework.nodes;

import com.vutbr.homework.game.Player;

public class FuelStationPlanet extends Planet {
    public FuelStationPlanet(String name, int id) {
        super(name, id);
    }

    @Override
    public boolean event(Player player) {
        System.out.println("fuel");
        return false;
    }

    @Override
    public void printEventDesc() {
        System.out.println("fuel");
    }
}
