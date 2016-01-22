package com.m2dl.maf.makeafocal.util;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.content.res.Resources;
import android.util.Log;


import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.nearby.messages.Message;
import com.m2dl.maf.makeafocal.R;
import com.m2dl.maf.makeafocal.model.PointOfInterest;
import com.m2dl.maf.makeafocal.model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by florent on 22/01/16.
 */
public class JsonMarkerParser extends AsyncTask<Void, Void, Void>{

    private InputStream in;

    private List<PointOfInterest> markers;

    private final float COLOR_CARTON = BitmapDescriptorFactory.HUE_ORANGE;
    private final float COLOR_PAPER = BitmapDescriptorFactory.HUE_CYAN;
    private final float COLOR_BATTERY = BitmapDescriptorFactory.HUE_VIOLET;
    private final float COLOR_TEXTILE = BitmapDescriptorFactory.HUE_ROSE;
    private final float COLOR_GLASS = BitmapDescriptorFactory.HUE_GREEN;
    private final float COLOR_BUILDING = BitmapDescriptorFactory.HUE_RED;


    public JsonMarkerParser(Resources resources) {
        this.in = resources.openRawResource(R.raw.markers);
        markers = new ArrayList<PointOfInterest>();
    }

    public void getPointsOfInterest() {
        try {
            JsonReader reader = new JsonReader(
                    new InputStreamReader(in, "UTF-8"));

            reader.beginArray();
            while (reader.hasNext()) {
                markers.add(readMessage(reader));
            }
            reader.endArray();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public PointOfInterest readMessage(JsonReader reader) throws IOException {
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

        return new PointOfInterest(title, latitude, longitude, color);
    }

    @Override
    protected Void doInBackground(Void... params) {
        getPointsOfInterest();
        return null;
    }

    public List<PointOfInterest> getMarkers() {
        return markers;
    }
}
