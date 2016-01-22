package com.m2dl.maf.makeafocal.model;

/**
 * Created by florent on 22/01/16.
 */
public class PointOfInterest {
    private String name;
    private float latitude;
    private float longitude;
    private float color;

    public PointOfInterest(
            final String title,
            final float lat, final float lng,
            final float colorString) {
        name = title;
        latitude = lat;
        longitude = lng;
        color = colorString;
    }

    public String getName() {
        return name;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getColor() {
        return color;
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
