package com.m2dl.maf.makeafocal.model.pointofinterest;


/**
 * Created by florent on 22/01/16.
 */
public class MarkerPointOfInterest extends PointOfInterest {
    private float latitude;
    private float longitude;
    private float color;


    public MarkerPointOfInterest(
            final String title,
            final float lat, final float lng,
            final float colorString) {
        super(title);
        color = colorString;
        latitude = lat;
        longitude = lng;
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
                ", lat=" + latitude +
                ", lng=" + longitude +
                '}';
    }
}
