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
                    ), p.getName()
            );
        }
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56758,1.46950), new LatLng(43.56773,1.46989), new LatLng(43.56733,1.47026), new LatLng(43.56716,1.46991)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56666,1.46950), new LatLng(43.56674,1.46967), new LatLng(43.56667,1.46975), new LatLng(43.56657,1.46958)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.BLUE));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56497,1.46513), new LatLng(43.56504,1.46529), new LatLng(43.56461,1.4657), new LatLng(43.56454,1.46554)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56542,1.46363), new LatLng(43.56554,1.4639), new LatLng(43.5652,1.46422), new LatLng(43.56508,1.46391)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56372, 1.46478), new LatLng(43.56393, 1.46529), new LatLng(43.56426, 1.46496), new LatLng(43.56403, 1.46448)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56349, 1.46428), new LatLng(43.56362, 1.4646), new LatLng(43.56323, 1.46499), new LatLng(43.56311, 1.46473)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.5632, 1.465620), new LatLng(43.56335, 1.4659), new LatLng(43.56263, 1.46661), new LatLng(43.56249, 1.46632)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56377,1.46168), new LatLng(43.56384,1.46181), new LatLng(43.56375,1.46195), new LatLng(43.56367,1.4618)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56123,1.46364), new LatLng(43.56137,1.46392), new LatLng(43.56086,1.46442), new LatLng(43.56072,1.46414)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56151,1.46595), new LatLng(43.56161,1.46623), new LatLng(43.56186,1.46599), new LatLng(43.56175,1.46571)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56146,1.46600), new LatLng(43.56154,1.46615), new LatLng(43.56112,1.46656), new LatLng(43.56104,1.46641)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56202,1.46603), new LatLng(43.5621,1.46618), new LatLng(43.5615,1.46678), new LatLng(43.56142,1.46663)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56146,1.46709), new LatLng(43.56156,1.4673), new LatLng(43.5614,1.46745), new LatLng(43.5613,1.46725)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56245,1.4669), new LatLng(43.56253,1.46706), new LatLng(43.5623,1.46728), new LatLng(43.56222,1.46713)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.5618,1.4677), new LatLng(43.56197,1.46805), new LatLng(43.56174,1.46827), new LatLng(43.56157,1.46793)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56263,1.46908), new LatLng(43.56277,1.46939), new LatLng(43.5625,1.46965), new LatLng(43.56236,1.46932)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56189,1.46903), new LatLng(43.56201,1.4693), new LatLng(43.56185,1.46944), new LatLng(43.56173,1.46916)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56134,1.46839), new LatLng(43.56141,1.46855), new LatLng(43.56101,1.46896), new LatLng(43.56093,1.46881)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56141,1.46894), new LatLng(43.56169,1.46951), new LatLng(43.56105,1.47007), new LatLng(43.56079,1.46955)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56153, 1.47001), new LatLng(43.56088, 1.47064), new LatLng(43.56107, 1.47108), new LatLng(43.56173, 1.47041)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.55933, 1.47231), new LatLng(43.55939, 1.47246), new LatLng(43.55886, 1.47299), new LatLng(43.55879, 1.47284)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56076, 1.46724), new LatLng(43.56084, 1.46739), new LatLng(43.55942, 1.46883), new LatLng(43.55934, 1.46868)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.56025, 1.46732), new LatLng(43.56032, 1.46748), new LatLng(43.55976, 1.46805), new LatLng(43.55968, 1.46787)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));
//        map.addPolygon(new PolygonOptions().add(new LatLng(43.55844, 1.46891), new LatLng(43.55852, 1.46906), new LatLng(43.55798, 1.4696), new LatLng(43.5579, 1.46945)).strokeColor(Color.argb(170, 0, 121, 107)).fillColor(Color.argb(170, 0, 121, 107)));

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
