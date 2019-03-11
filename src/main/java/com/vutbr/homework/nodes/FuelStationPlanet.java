package com.vutbr.homework.nodes;

import com.vutbr.homework.game.Player;

import java.util.Scanner;

public class FuelStationPlanet extends Planet {
    private static final double CONVERSION_RATIO = 2.58;

    public FuelStationPlanet(String name, int id, String planetDesc, String eventDesc) {
        super(name, id, planetDesc, eventDesc);
    }

    @Override
    public boolean event(Player player) {
        Scanner sc = new Scanner(System.in);
        int numberOfUnitsToBuy;
        int canBuyFuel;
        double prize;

        player.printStatus();
        System.out.println(eventDesc);
        System.out.println("Jedna jednotka paliva stoji " + CONVERSION_RATIO + " kreditov\nKolko jednotiek chces kupit ?");

        do {
            numberOfUnitsToBuy = sc.nextInt();
            prize = (double) numberOfUnitsToBuy * CONVERSION_RATIO;
            canBuyFuel = player.canBuyFuel(numberOfUnitsToBuy, prize);
            switch (canBuyFuel) {
                case 1:
                    System.out.format("Nemozes mat viac paliva ako %.0f!\n", + Player.MAX_FUEL());
                    break;
                case 2:
                    System.out.print("Nemas dost penazi !\n");
                    break;
            }
        } while (canBuyFuel != 0);

        player.setFuel(player.getFuel() + numberOfUnitsToBuy);
        player.setMoney(player.getMoney() - prize);
        return false;
    }
}
