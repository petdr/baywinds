package com.emailross.ozwinds;

import java.util.*;
import java.net.*;
import java.io.*;

import org.json.*;
import com.google.gson.stream.*;

import android.util.Log;

/**
 * The current observations of port phillip bay
 */
public class Observations implements Serializable {
    private boolean have_observations = false;
    private Location location;
    private List<Observation> observations;
    private boolean problem = false;
    private String problem_report = "";
    private long last_observation = 0;

    private final int ONE_SECOND = 1000;
    private final int ONE_MINUTE = 60 * ONE_SECOND;

    public Observations(Location l) {
        setLocation(l);
    }

    public void setLocation(Location l) {
        if (location != l) {
            location = l;
            force_refresh();
        }
    }

    public void refresh() {
        // Only refresh if we are one minute into a new 30 minute block
        long current_observation = ( new Date().getTime() - ONE_MINUTE ) / (30 * ONE_MINUTE);
        Log.d("BayWinds", "last_observation: " + last_observation);
        Log.d("BayWinds", "current_observation: " + current_observation);

        if (current_observation != last_observation || !have_observations) {
            force_refresh();

            if (have_observations) {
                last_observation = current_observation;
            }
        }
    }

    public void force_refresh() {
        try {
            observations = new ArrayList();

            for (Station station : location.getStations()) {
                JsonReader reader = getJsonReader(station.getObservationsURI());
                Observation o = getFirstObservation(reader);
                observations.add(o);
            }

            have_observations = true;

        } catch (Exception e) {
            problem = true;
            problem_report = e.toString();

            Log.e("BayWinds", problem_report);

            observations = new ArrayList();
            Observation o;
            o = new Observation(problem_report, "-", "-", "-");
            observations.add(o);
        }

    }

    public List<Observation> getObservations() {
        return observations;
    }

    public boolean haveObservations() {
        return have_observations;
    }

    public JsonReader getJsonReader(String u) throws MalformedURLException, IOException {
        URL url = new URL(u);
        URLConnection urlc = url.openConnection();
        InputStream is = urlc.getInputStream();

        return new JsonReader(new InputStreamReader(is, "UTF-8"));
    }

    private Observation getFirstObservation(JsonReader reader) throws IOException {
        reader.beginObject();   // {
        reader.nextName();      // "observations":
        reader.beginObject();   // {
        reader.nextName();      // "notice":
        reader.skipValue();     // ....
        reader.nextName();      // "header:
        reader.skipValue();     // ....
        reader.nextName();      // "data":
        reader.beginArray();    // [

        return readObservation(reader);
    }

    /**
     * Read a single observation from the reader.
     */
    private Observation readObservation(JsonReader reader) throws IOException {
        String name = "";
        String wind_spd_kt = "";
        String gust_kt = "";
        String wind_dir = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String id = reader.nextName();
            if (id.equals("name")) {
                name = reader.nextString();
            } else if (id.equals("wind_spd_kt")) {
                wind_spd_kt = reader.nextString();
            } else if (id.equals("gust_kt")) {
                gust_kt = reader.nextString();
            } else if (id.equals("wind_dir")) {
                wind_dir = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new Observation(name, wind_spd_kt, gust_kt, wind_dir);
    }
}
// vim: ts=4 sw=4 et
