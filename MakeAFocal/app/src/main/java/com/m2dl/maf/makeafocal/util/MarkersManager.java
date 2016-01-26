package com.m2dl.maf.makeafocal.util;

import android.app.Activity;
import android.content.res.Resources;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.m2dl.maf.makeafocal.model.PointOfInterest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by florent on 23/01/16.
 */
public class MarkersManager {

    private Map<String, List<Marker>> mapTagMarkers;

    private JsonMarkerParser parser;

    private Activity context;

    public MarkersManager(final Activity activity) {
        context = activity;
        mapTagMarkers = new TreeMap<>();
        parser = new JsonMarkerParser(activity.getResources());
        parser.execute();


    }

    private void addPointsOfInterest(final List<PointOfInterest> points, final GoogleMap map) {
        for (PointOfInterest p : points) {
            addMarker(
                map.addMarker(new MarkerOptions()
                    .position(new LatLng(p.getLatitude(), p.getLongitude()))
                    .title(p.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(p.getColor()))));
        }
    }

    public void addMarker(final Marker marker) {
        String tagName = marker.getTitle().toLowerCase();

        if (mapTagMarkers.containsKey(tagName)) {
            mapTagMarkers.get(tagName).add(marker);
        } else {
            List<Marker> list = new ArrayList<>();
            list.add(marker);
            mapTagMarkers.put(tagName, list);
        }

    }

    public void addPhotoMarker(final Marker marker) {
        String[] tags = marker.getTitle().toLowerCase()
                .replace("[","").replace("]", "")
                .replace("#","")
                .replace(" ","")
                .split(",");

        for (String tag : tags) {

            if (mapTagMarkers.containsKey(tag)) {
                mapTagMarkers.get(tag).add(marker);
            } else {
                List<Marker> list = new ArrayList<>();
                list.add(marker);
                mapTagMarkers.put(tag, list);
            }
        }

    }

    public void setVisibleTags(final String[] tags) {
        context.runOnUiThread(new Runnable() {
            public void run() {

                for (String other : mapTagMarkers.keySet()) {
                    if (!Arrays.asList(tags).contains(other)) {
                        for (Marker m : mapTagMarkers.get(other)) {
                            m.setVisible(false);
                        }
                    }
                }

                for (String tag : tags) {
                    if (mapTagMarkers.containsKey(tag)) {
                        for (Marker m : mapTagMarkers.get(tag)) {
                            m.setVisible(true);
                        }
                    }
                }
            }
        });

    }

    public void init(GoogleMap map) {
        addPointsOfInterest(parser.getPointsOfInterest(), map);
    }

    public void setVisibleAllTags() {
        context.runOnUiThread(new Runnable() {
            public void run() {
                for (String tag : mapTagMarkers.keySet()) {
                    for (Marker m : mapTagMarkers.get(tag)) {
                        m.setVisible(true);
                    }
                }
            }
        });
    }




}
