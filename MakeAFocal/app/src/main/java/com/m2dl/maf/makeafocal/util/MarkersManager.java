package com.m2dl.maf.makeafocal.util;

import android.content.res.Resources;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.m2dl.maf.makeafocal.model.PointOfInterest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florent on 23/01/16.
 */
public class MarkersManager {

    private List<Marker> markers;

    private JsonMarkerParser parser;

    private GoogleMap map;

    public MarkersManager(final Resources resources, GoogleMap gMap) {
        markers = new ArrayList<>();
        map = gMap;
        parser = new JsonMarkerParser(resources);
        parser.execute();

        addPointsOfInterest(parser.getPointsOfInterest());
    }

    private void addPointsOfInterest(final List<PointOfInterest> points) {

        for (PointOfInterest p : points) {
            markers.add(
                map.addMarker(new MarkerOptions()
                    .position(new LatLng(p.getLatitude(), p.getLongitude()))
                    .title(p.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(p.getColor()))));
        }
    }


}
