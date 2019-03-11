package com.vutbr.homework.game;

public class Player {
    private double oreStorage = 10;
    private double fuel = 2;
    private double money = 300;
    private double hull = 50;
    private int numOfKeys = 0;
    private static final double MAX_FUEL = 5000;
    private static final double MAX_HULL = 100;

    public static double MAX_FUEL() {
        return MAX_FUEL;
    }

    public static double MAX_HULL() {
        return MAX_HULL;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getHull() {
        return hull;
    }

    public void setHull(double hull) {
        this.hull = hull;
    }

    public int getNumOfKeys() {
        return numOfKeys;
    }

    public double getOreStorage() {
        return oreStorage;
    }

    public void setOreStorage(double oreStorage) {
        this.oreStorage = oreStorage;
    }

    public void incrementNumOfKeys() {
        this.numOfKeys++;
    }

    public int canBuyFuel(int numberOfUnitsToBuy, double prize) {
        if (fuel + numberOfUnitsToBuy > MAX_FUEL) {
            return 1;
        } else if (money - prize < 0) {
            return 2;
        } else {
            return 0;
        }
    }

    public int canRepair(int numberOfHull, double prize) {
        if (hull + numberOfHull > MAX_HULL) {
            return 1;
        } else if (money - prize < 0) {
            return 2;
        } else {
            return 0;
        }
    }

    public void printStatus() {
        System.out.format("palivo : %.0f jednotiek\nkredity : %.2f kreditov\nsila trupu : %.0f%%\npocet klucov : %d/2\n",
                fuel, money, hull, numOfKeys);
    }
}
