package com.m2dl.maf.makeafocal.model.pointofinterest;

import android.util.Pair;

import java.util.List;

/**
 * Created by florent on 26/01/16.
 */
public class PolygonPointOfInterest extends PointOfInterest {

    private List<Pair<Double, Double>> listOfPoints;

    public PolygonPointOfInterest(final String title, float color) {
        super(title, color);
    }

    public List<Pair<Double, Double>> getListOfPoints() {
        return listOfPoints;
    }
}
