package com.vutbr.homework.game;

public class Player {
    private int oreStorage = 10;
    private int fuel = 2;
    private double money = 300;
    private int hull = 5;
    private int numOfKeys = 0;
    private static final int MAX_FUEL = 5000;
    private static final int MAX_HULL = 100;

    public static double MAX_FUEL() {
        return MAX_FUEL;
    }

    public static double MAX_HULL() {
        return MAX_HULL;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getHull() {
        return hull;
    }

    public void setHull(int hull) {
        this.hull = hull;
    }

    public int getNumOfKeys() {
        return numOfKeys;
    }

    public int getOreStorage() {
        return oreStorage;
    }

    public void setOreStorage(int oreStorage) {
        this.oreStorage = oreStorage;
    }

    public void appendOreStorage(int oreStorageToAppend) {
        this.oreStorage += oreStorageToAppend;
    }

    public void deductHull(int hullToDeduct) {
        this.hull -= hullToDeduct;
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

    boolean isHullBroken() {
        return hull <= 0;
    }

    public void printStatus() {
        System.out.format("palivo : %d jednotiek\nton mineralov : %d\nkredity : %.2f kreditov\nsila trupu : %d%%"
                        + "\n" + "pocet klucov : %d/2\n", fuel, oreStorage, money, hull, numOfKeys);
    }
}