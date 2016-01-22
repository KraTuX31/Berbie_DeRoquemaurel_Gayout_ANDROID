package com.m2dl.maf.makeafocal.util;

import android.util.JsonReader;
import android.util.JsonToken;
import android.content.res.Resources;
import android.util.Log;


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
public class JsonMarkerParser {

    private InputStream in;

    private List<PointOfInterest> markers;

    public JsonMarkerParser(Resources resources) {
        this.in = resources.openRawResource(R.raw.markers);
        markers = new ArrayList<PointOfInterest>();
        getPointsOfInterest();
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
        String name = null;
        float latitude = 0;
        float longitude = 0;
        String color = null;

        reader.beginObject();
        String elt;
        while (reader.hasNext()) {
            elt = reader.nextName();
            if (elt.equals("name")) {
                name = reader.nextString();
                if (name.equals("Carton")) {
                    color = "#DBA901";
                } else if (name.equals("Papier")) {
                    color = "#FAFAFA";
                } else if (name.equals("Textile")) {
                    color = "#9F81F7";
                } else if (name.equals("Verre")) {
                    color = "#81F781";
                } else {
                    color = "#2E2E2E";
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
        PointOfInterest p = new PointOfInterest(name, latitude, longitude, color);
        Log.d("PARSER", p.toString());
        return p;
    }

}
