package com.m2dl.maf.makeafocal.model.pointofinterest;

import android.util.Pair;

import java.util.List;
import java.util.Set;

/**
 * Created by florent on 26/01/16.
 */
public class PolygonPointOfInterest extends PointOfInterest {

    private Set<Pair<Double, Double>> listOfPoints;
    private int color;

    public PolygonPointOfInterest(final String title, int colorInt,
                                  final Set<Pair<Double, Double>> points) {
        super(title);
        listOfPoints = points;
        color = colorInt;
    }

    public Set<Pair<Double, Double>> getListOfPoints() {
        return listOfPoints;
    }

    public int getColor() {
        return color;
    }
}
