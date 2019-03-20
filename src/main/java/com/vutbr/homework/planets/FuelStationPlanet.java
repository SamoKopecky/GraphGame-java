package com.vutbr.homework.planets;

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

        System.out.println(player);
        System.out.println(eventDesc);
        System.out.println("Jedna jednotka paliva stoji " + CONVERSION_RATIO + " kreditov\nKolko jednotiek chces " +
                "kupit ? (max : " + (int) (player.getMoney() / CONVERSION_RATIO) + ")");

        do {
            while (!sc.hasNextInt()) {
                sc.next();
                System.out.println("zadaj iba cislo !");
            }
            numberOfUnitsToBuy = sc.nextInt();
            prize = (double) numberOfUnitsToBuy * CONVERSION_RATIO;
            canBuyFuel = player.canBuyFuel(numberOfUnitsToBuy, prize);
            switch (canBuyFuel) {
                case 1:
                    System.out.format("Nemozes mat viac paliva ako %.0f!\n", Player.getMAX_FUEL());
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