package com.m2dl.maf.makeafocal.model;

/**
 * Created by florent on 21/01/16.
 */
public class Tag {

    private String name;
    private int x;
    private int y;

    public Tag(final String title) {
        name =  title;
        x = 0;
        y = 0;
    }

    public String getName() {
        return name;
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

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "#" + name;
    }
}
