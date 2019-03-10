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
}
