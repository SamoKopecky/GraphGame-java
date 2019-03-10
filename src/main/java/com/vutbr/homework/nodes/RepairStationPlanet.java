package com.vutbr.homework.nodes;

import com.vutbr.homework.game.Player;

public class RepairStationPlanet extends Planet {
    public RepairStationPlanet(String name, int id) {
        super(name, id);
    }

    @Override
    public boolean event(Player player) {
        System.out.println("repair");
        return false;
    }
}
