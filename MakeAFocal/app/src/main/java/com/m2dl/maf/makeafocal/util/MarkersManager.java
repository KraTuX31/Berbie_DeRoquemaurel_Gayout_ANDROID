package com.m2dl.maf.makeafocal.util;

import android.app.Activity;
import android.graphics.Color;
import android.util.Pair;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.m2dl.maf.makeafocal.model.pointofinterest.MarkerPointOfInterest;
import com.m2dl.maf.makeafocal.model.pointofinterest.PolygonPointOfInterest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by florent on 23/01/16.
 */
public class MarkersManager {

    private Map<String, List<Marker>> mapTagMarkers;
    private Map<String, List<Polygon>> mapTagPolygons;

    private JsonMarkerParser parser;

    private Activity context;

    public MarkersManager(final Activity activity) {
        context = activity;
        mapTagMarkers = new TreeMap<>();
        mapTagPolygons = new TreeMap<>();
        parser = new JsonMarkerParser(activity.getResources());
        parser.execute();


    }

    private void addPointsOfInterest(
            final List<MarkerPointOfInterest> markers,
            final List<PolygonPointOfInterest> polygons,
            final GoogleMap map) {
        for (MarkerPointOfInterest m : markers) {
            addMarker(
                map.addMarker(new MarkerOptions()
                    .position(new LatLng(m.getLatitude(), m.getLongitude()))
                    .title(m.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(m.getColor()))));
        }

        LatLng[] latLng = new LatLng[4];
        int i;
        for (PolygonPointOfInterest p : polygons) {
            i = 0;
            for (Pair<Double, Double> pair : p.getListOfPoints()) {
                latLng[i] = new LatLng(pair.first, pair.second);
                i++;
            }
            addPolygon(
                    map.addPolygon(new PolygonOptions()
                        .add(latLng[0], latLng[1], latLng[2], latLng[3])
                        .strokeColor((int) p.getColor())
                        .fillColor((int) p.getColor())
                    ), p.getName().toLowerCase()
            );
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

    public void addPolygon(final Polygon polygon, final String name) {
        String tagName = name.toLowerCase();

        if (mapTagPolygons.containsKey(tagName)) {
            mapTagPolygons.get(tagName).add(polygon);
        } else {
            List<Polygon> list = new ArrayList<>();
            list.add(polygon);
            mapTagPolygons.put(tagName, list);
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

                for (String tag : mapTagMarkers.keySet()) {
                    if (!Arrays.asList(tags).contains(tag)) {
                        for (Marker m : mapTagMarkers.get(tag)) {
                            m.setVisible(false);
                        }
                    } else if (mapTagMarkers.containsKey(tag)) {
                        for (Marker m : mapTagMarkers.get(tag)) {
                            m.setVisible(true);
                        }
                    }
                }

                for (String tag : mapTagPolygons.keySet()) {
                    if (!Arrays.asList(tags).contains(tag)) {
                        for (Polygon p : mapTagPolygons.get(tag)) {
                            p.setVisible(false);
                        }
                    } else if (mapTagMarkers.containsKey(tag)) {
                        for (Polygon p : mapTagPolygons.get(tag)) {
                            p.setVisible(true);
                        }
                    }
                }

            }
        });

    }

    public void init(GoogleMap map) {
        addPointsOfInterest(parser.getMarkers(), parser.getPolygons(), map);

    }

    public void setVisibleAllTags() {
        context.runOnUiThread(new Runnable() {
            public void run() {
                for (String tag : mapTagMarkers.keySet()) {
                    for (Marker m : mapTagMarkers.get(tag)) {
                        m.setVisible(true);
                    }
                }

                for (String tag : mapTagPolygons.keySet()) {
                    for (Polygon p : mapTagPolygons.get(tag)) {
                        p.setVisible(true);
                    }
                }
            }
        });
    }




}
