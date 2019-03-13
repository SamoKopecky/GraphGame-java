package com.vutbr.homework.planets;

import com.vutbr.homework.game.Player;

import java.util.Scanner;

public class RepairStationPlanet extends Planet {
    private static final double HULL_CONVERSION_RATIO = 20;
    private static final double ORE_CONVERSION_RATIO = 5.94;

    public RepairStationPlanet(String name, int id, String planetDesc, String eventDesc) {
        super(name, id, planetDesc, eventDesc);
    }

    @Override
    public boolean event(Player player) {
        Scanner sc = new Scanner(System.in);
        int hullToRepair;
        int canRepair;
        double prize;
        double moneyGained = player.getMineralsStorage() * ORE_CONVERSION_RATIO;

        System.out.println(eventDesc);
        if (moneyGained > 0) {
            System.out.format("Predal si " + player.getMineralsStorage() + " kg surovin a ziskal si %.2f kreditov\n", moneyGained);
            player.setMoney(player.getMoney() + moneyGained);
            player.setMineralsStorage(0);
        }
        player.printStatus();

        System.out.println("Jedno percento trupu stoji " + HULL_CONVERSION_RATIO + " kreditov\nKolko percent chces opravit ?");

        do {
            hullToRepair = sc.nextInt();
            prize = (double) hullToRepair * HULL_CONVERSION_RATIO;
            canRepair = player.canRepair(hullToRepair, prize);
            switch (canRepair) {
                case 1:
                    System.out.format("Nemozes sa opravit viac ako %.0f%%!\n", Player.MAX_HULL());
                    break;
                case 2:
                    System.out.print("Nemas dost penazi !\n");
                    break;
            }
        } while (canRepair != 0);

        player.setHull(player.getHull() + hullToRepair);
        player.setMoney(player.getMoney() - prize);
        return false;
    }
}
