package com.m2dl.maf.makeafocal.model;

/**
 * Created by florent on 22/01/16.
 */
public class PointOfInterest {
    private String title;
    private float latitude;
    private float longitude;
    private String color;

    public PointOfInterest(final String title, float latitude, float longitude, String color) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.color = color;
    }

    @Override
    public String toString() {
        return "PointOfInterest{" +
                "title='" + title + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", color='" + color + '\'' +
                '}';
    }
}
