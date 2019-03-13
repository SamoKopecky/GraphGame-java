package com.vutbr.homework.planets;

import com.vutbr.homework.game.Player;

public class EndPlanet extends Planet {
    public EndPlanet(String name, int id, String planetDesc, String eventDesc) {
        super(name, id, planetDesc, eventDesc);
    }

    @Override
    public boolean event(Player player) {
        player.printStatus();
        System.out.println(eventDesc);
        if (player.getNumOfKeys() == 2) {
            System.out.println("Gratulujem dokoncil si hru !");
            return true;
        } else {
            System.out.println("Musis najprv pozbierat vsetky kluce aby si mohol prest portalom");
            return false;
        }
    }
}
