package com.vutbr.homework.paths;

import com.vutbr.homework.game.Player;

public class Path {
    private int length;
    private String pathDesc;
    private PathType type;
    private boolean visitedPath;
    private int pathID;

    public int getLength() {
        return length;
    }

    public Path(int length, String pathDesc, PathType type, int pathID) {
        visitedPath = false;
        this.type = type;
        this.pathDesc = pathDesc;
        this.length = length;
        this.pathID = pathID;
    }

    public String getPathDesc() {
        return pathDesc;
    }

    public void event(Player player) {
        player.setFuel(player.getFuel() - length);
        if (!visitedPath) {
            player.setHull(player.getHull() - type.getHull());
            player.appendMineralsStorage(type.getMinerals());
            player.printStatus();
            System.out.println("Stratil si " + type.getHull() + "% trupu a ziskal si " + type.getMinerals() + " ton " +
                    "mineralov a cesta ta stala " + length + " paliva");
            visitedPath = true;
        } else {
            player.printStatus();
            System.out.println("cesta ta stala " + length + " paliva ale nic si nenasiel");
        }
    }


    public int getPathID() {
        return pathID;
    }
}
