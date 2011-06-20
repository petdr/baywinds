package com.emailross.baywinds;

import java.util.*;
import java.net.*;
import java.io.*;

import org.json.*;

import android.util.Log;

/**
 * The current observations of port phillip bay
 */
public class Observations implements Serializable {
    private List<Observation> observations;
    private boolean problem = false;
    private String problem_report = "";

    public Observations(Location location) {
        try {
            observations = new ArrayList();

            for (Station station : location.getStations()) {
                // Get the json data
                String json_string = getJSONString(station.getObservationsURI());
                JSONObject json_obj = new JSONObject(json_string);
                JSONArray json_data = json_obj.getJSONObject("observations").getJSONArray("data");
                JSONObject json_latest = json_data.getJSONObject(0);

                Observation o;
                o = new Observation(station.getName(),
                        json_latest.getString("wind_spd_kt"),
                        json_latest.getString("gust_kt"),
                        json_latest.getString("wind_dir"));
                observations.add(o);
            }

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

    public String getJSONString(String u) throws MalformedURLException, IOException {
        URL url = new URL(u);
        URLConnection urlc = url.openConnection();
        InputStream is = urlc.getInputStream();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return new String(buffer.toByteArray());
    }


}
// vim: ts=4 sw=4 et
