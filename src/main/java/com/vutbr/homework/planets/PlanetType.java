package com.vutbr.homework.planets;

public enum PlanetType {

    FRIENDLY(85, 0),
    HOSTILE(170, 20),
    NEUTRAL(43, 10),
    DEADLY(43, 30),
    NONE(0, 0);

    private int minerals, hull;

    PlanetType(int minerals, int hull) {
        this.minerals = minerals;
        this.hull = hull;
    }

    public int getMinerals() {
        return minerals;
    }

    public int getHull() {
        return hull;
    }
}
