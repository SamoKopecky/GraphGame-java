package com.vutbr.homework.game;

public class Player {
    private int fuel = Integer.MAX_VALUE;
    private int money = 0;
    private int hull = 100;
    private int numOfKeys = 0;

    public int getNumOfKeys() {
        return numOfKeys;
    }

    public void incrementNumOfKeys() {
        numOfKeys++;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getHull() {
        return hull;
    }

    public void setHull(int hull) {
        this.hull = hull;
    }
}
