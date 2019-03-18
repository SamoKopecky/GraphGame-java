package com.vutbr.homework.game;

public class Player {
    private int mineralsStorage = 10;
    private int fuel = 200;
    private double money = 0;
    private int hull = 100;
    private int numOfKeys = 0;
    private static final int MAX_FUEL = 5000;
    private static final int MAX_HULL = 100;

    public static double getMAX_FUEL() {
        return MAX_FUEL;
    }

    public static double getMAX_HULL() {
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

    public void deductHull(int hullToDeduct) {
        this.hull -= hullToDeduct;
    }

    public int getNumOfKeys() {
        return numOfKeys;
    }

    public int getMineralsStorage() {
        return mineralsStorage;
    }

    public void setMineralsStorage(int mineralsStorage) {
        this.mineralsStorage = mineralsStorage;
    }

    public void appendMineralsStorage(int mineralsStorageToAppend) {
        this.mineralsStorage += mineralsStorageToAppend;
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

    @Override
    public String toString() {
        return String.format("palivo : %d jednotiek\nton mineralov : %d\nkredity : %.2f kreditov\nsila trupu : %d%%"
                + "\n" + "pocet klucov : %d/2\n", fuel, mineralsStorage, money, hull, numOfKeys);
    }

    boolean isPlayerDead() {
        if (hull <= 0) {
            System.out.println("pokazila sa ti lod, prehral si");
            return true;
        } else if (fuel <= 0) {
            System.out.println("doslo ti palivo, prehral si");
            return true;
        } else {
            return false;
        }
    }
}