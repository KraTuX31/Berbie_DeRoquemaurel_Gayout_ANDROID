package com.m2dl.maf.makeafocal.model;

import android.content.Context;
import android.util.Pair;

/**
 * Created by aroquemaurel on 21/01/16.
 */
public class Zone {

    private int x;
    private int y;
    private int size;

    /**
     * A zone in a picture
     * @param pos The position of the zone (x, y)
     * @param size The diameter size in pixels
     */
    public Zone(final int i, final int j, final int size) {
        x = i;
        y = j;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
}
