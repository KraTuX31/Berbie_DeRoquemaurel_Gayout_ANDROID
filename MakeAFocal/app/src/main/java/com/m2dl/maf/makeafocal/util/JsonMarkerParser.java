package com.m2dl.maf.makeafocal.util;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Pair;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.m2dl.maf.makeafocal.R;
import com.m2dl.maf.makeafocal.model.pointofinterest.MarkerPointOfInterest;
import com.m2dl.maf.makeafocal.model.pointofinterest.PolygonPointOfInterest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by florent on 22/01/16.
 */
public class JsonMarkerParser extends AsyncTask<Void, Void, Void>{

    private InputStream in;

    private List<MarkerPointOfInterest> markers;
    private List<PolygonPointOfInterest> polygons;

    private final float COLOR_CARTON = BitmapDescriptorFactory.HUE_ORANGE;
    private final float COLOR_PAPER = BitmapDescriptorFactory.HUE_CYAN;
    private final float COLOR_BATTERY = BitmapDescriptorFactory.HUE_VIOLET;
    private final float COLOR_TEXTILE = BitmapDescriptorFactory.HUE_ROSE;
    private final float COLOR_GLASS = BitmapDescriptorFactory.HUE_GREEN;
    private final float COLOR_BUILDING = BitmapDescriptorFactory.HUE_RED;


    public JsonMarkerParser(Resources resources) {
        this.in = resources.openRawResource(R.raw.pointsofinterest);
        markers = new ArrayList<>();
        polygons = new ArrayList<>();
    }

    public void parse() throws JSONException {
        try {
            JsonReader reader = new JsonReader(
                    new InputStreamReader(in, "UTF-8"));

            reader.beginObject();
            String elt;
            while (reader.hasNext()) {
                elt = reader.nextName();
                if (elt.equals("markers")) {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        markers.add(readMarkerMessage(reader));
                    }
                    reader.endArray();
                } else if (elt.equals("zones")) {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        polygons.add(readPolygonMessage(reader));
                    }
                    reader.endArray();
                }
            }
            reader.endObject();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private PolygonPointOfInterest readPolygonMessage(JsonReader reader) throws IOException {
        String title = null;
        Set<Pair<Double, Double>> list = new LinkedHashSet<>();
        Double lat = null;
        Double lng = null;
        int color = 0;

        reader.beginObject();
        String elt;
        while (reader.hasNext()) {
            elt = reader.nextName();
            if (elt.equals("name")) {
                title = reader.nextString();
                if (title.equals("Carton")) {
                    color = Color.argb(170, 255, 170, 0);
                } else if (title.equals("Papier")) {
                    color = Color.argb(170, 0, 207, 255);
                } else if (title.equals("Pile")) {
                    color = Color.argb(170, 155, 0, 255);
                } else {
                    color = Color.argb(170, 130, 119, 23);
                }
            } else if (elt.equals("points")) {
                reader.beginArray();
                while (reader.hasNext()) {
                    lat = null;
                    lng = null;
                    reader.beginObject();
                    while (reader.hasNext()) {
                        elt = reader.nextName();
                        if (elt.equals("latitude")) {
                            lat = reader.nextDouble();
                        } else if (elt.equals("longitude")) {
                            lng = reader.nextDouble();
                        }
                        if (lat != null && lng != null) {
                            list.add(new Pair<Double, Double>(lat, lng));
                        }
                    }
                    reader.endObject();
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new PolygonPointOfInterest(title, color, list);
    }


    public MarkerPointOfInterest readMarkerMessage(JsonReader reader) throws IOException {
        String title = null;
        float latitude = 0F;
        float longitude = 0F;
        float color = 0F;

        reader.beginObject();
        String elt;
        while (reader.hasNext()) {
            elt = reader.nextName();
            if (elt.equals("name")) {
                title = reader.nextString();
                if (title.equals("Carton")) {
                    color = COLOR_CARTON;
                } else if (title.equals("Papier")) {
                    color = COLOR_PAPER;
                } else if (title.equals("Textile")) {
                    color = COLOR_TEXTILE;
                } else if (title.equals("Pile")) {
                    color = COLOR_BATTERY;
                } else if (title.equals("Verre")) {
                    color = COLOR_GLASS;
                } else {
                    color = COLOR_BUILDING;
                }
            } else if (elt.equals("latitude")) {
                latitude = (float) reader.nextDouble();
            } else if (elt.equals("longitude")) {
                longitude = (float) reader.nextDouble();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new MarkerPointOfInterest(title, latitude, longitude, color);
    }



    @Override
    protected Void doInBackground(Void... params) {
        try {
            parse();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    public List<MarkerPointOfInterest> getMarkers() {
        return markers;
    }

    public List<PolygonPointOfInterest> getPolygons() {
        return polygons;
    }
}
