package com.vutbr.homework.nodes;

import com.vutbr.homework.game.Player;

public class EndPlanet extends Planet {
    private boolean firstKey = false;
    private boolean secondKey = false;

    public EndPlanet(String name, int id) {
        super(name, id);
    }

    @Override
    public boolean event(Player player) {
        System.out.println("Gratulujem dokoncil si hru !");
        return true;
    }
}
