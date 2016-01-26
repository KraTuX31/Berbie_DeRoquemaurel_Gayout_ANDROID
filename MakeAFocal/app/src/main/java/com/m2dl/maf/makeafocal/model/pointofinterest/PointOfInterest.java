package com.m2dl.maf.makeafocal.model.pointofinterest;

/**
 * Created by florent on 26/01/16.
 */
public class PointOfInterest {

    private String name;
    private float color;

    public PointOfInterest(final String title, final float colorString) {
        name = title;
        color = colorString;
    }

    public String getName() {
        return name;
    }
    public float getColor() {
        return color;
    }
}
