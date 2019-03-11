package com.vutbr.homework.nodes;

import com.vutbr.homework.game.Player;

public class BasicPlanet extends Planet {
    public BasicPlanet(String name, int id) {
        super(name, id);
    }

    @Override
    public boolean event(Player player) {
        System.out.println("obycajna planeta");
        this.visitedEvent = true;
        return false;
    }

    @Override
    public void printEventDesc() {
        System.out.println("ordinary planet");
    }
}
