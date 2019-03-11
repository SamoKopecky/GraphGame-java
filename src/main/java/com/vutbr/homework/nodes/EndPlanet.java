package com.vutbr.homework.nodes;

import com.vutbr.homework.game.Player;

public class EndPlanet extends Planet {

    public EndPlanet(String name, int id) {
        super(name, id);
    }

    @Override
    public boolean event(Player player) {
        if (player.getNumOfKeys() == 2) {
            System.out.println("Gratulujem dokoncil si hru !");
            return true;
        } else {
            System.out.println("Musis najprv pozbierat vsetky kluce aby si mohol prest portalom");
            return false;
        }
    }

    @Override
    public void printEventDesc() {
        System.out.println("koniec");
    }
}
