package com.m2dl.maf.makeafocal.model;

/**
 * Created by florent on 22/01/16.
 */
public class PointOfInterest {
    private String name;
    private float latitude;
    private float longitude;
    private String color;

    public PointOfInterest(
            final String title,
            final float lat, final float lng,
            final String colorString) {
        name = title;
        latitude = lat;
        longitude = lng;
        color = colorString;
    }


    @Override
    public String toString() {
        return "PointOfInterest{" +
                "name='" + name + '\'' +
                ", lat=" + latitude +
                ", lng=" + longitude +
                ", color='" + color + '\'' +
                '}';
    }
}
