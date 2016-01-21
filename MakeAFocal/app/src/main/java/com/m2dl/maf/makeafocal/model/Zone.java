package com.m2dl.maf.makeafocal.model;

import android.util.Pair;

/**
 * Created by aroquemaurel on 21/01/16.
 */
public class Zone {
    private Pair<Integer, Integer> position;
    private int size;

    /**
     * A zone in a picture
     * @param pos The position of the zone (x, y)
     * @param size The diameter size in pixels
     */
    public Zone(Pair<Integer, Integer> pos, final int size) {
        position = pos;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public void setPosition(Pair<Integer, Integer> position) {
        this.position = position;
    }
}
