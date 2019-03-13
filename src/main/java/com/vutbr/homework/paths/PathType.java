package com.vutbr.homework.paths;

public enum PathType {
    DANGEROUS(85, 10),
    EMPTY(0,0),
    FRIENDLY(43,0);

    private int minerals, hull;

    PathType(int minerals, int hull) {
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
