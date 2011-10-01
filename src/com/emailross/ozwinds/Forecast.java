package com.emailross.ozwinds;

import java.util.*;
import java.net.*;
import java.io.*;

import java.text.SimpleDateFormat;

import android.sax.*;
import android.util.Log;
import android.util.Xml;

import org.xml.sax.Attributes;

import java.io.Serializable;


/**
 * Get a forecast from the Australian BOM website and parse it
 * into a format of interest
 */
public class Forecast implements Serializable {
    private boolean have_forecast = false;
    private List<ForecastForDate> forecasts;
    private transient ForecastForDate latest_forecast;
    private transient String aac;
    private transient int index;

    /**
     * Create an empty forecast
     */
    public Forecast() {
        forecasts = new ArrayList();
    }

    public Forecast(final Location location) {
        try {
            final SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ssz");
            forecasts = new ArrayList();

            final Forecast f = this;

            // XXX This code doesn't work under the emulator, some problem with
            // ftp URI's not working under the emulator.
            URL url = new URL(location.getForecastUri());
            URLConnection c = url.openConnection();
            InputStream i = c.getInputStream();

            RootElement root = new RootElement("product");
            Element forecast = root.requireChild("forecast");
            Element area = forecast.requireChild("area");
            Element forecast_period = area.requireChild("forecast-period");
            Element text = forecast_period.requireChild("text");

            area.setStartElementListener(new StartElementListener() {
                public void start(Attributes attrs) {
                    f.aac = getValue(attrs, "aac");
                    Log.w("BayWinds", f.aac);
                }
            });
            forecast_period.setStartElementListener(new StartElementListener() {
                public void start(Attributes attrs) {
                    String index_string = getValue(attrs, "index");
                    if (index_string == null || index_string.equals("")) {
                        f.index = -1;
                    } else {
                        f.index = new Integer(index_string);

                        latest_forecast = new ForecastForDate();
                        latest_forecast.start = df.parse(getValue(attrs, "start-time-local"), new java.text.ParsePosition(0));
                        latest_forecast.forecast = "Unable to download forecast";

                        f.forecasts.add(f.index, latest_forecast);
                    }
                    Log.w("BayWinds", index_string);
                }
            });
            text.setEndTextElementListener(new EndTextElementListener() {
                public void end(String body) {
                    if (f.aac.equals(location.getArea()) && f.index >= 0) {
                        String forecast = body.replace(" Seas: ", "\n\nSeas:\n").replace("Winds: ", "Winds:\n") + "\n";
                        latest_forecast.forecast = forecast;
                    }
                }
            });

            Xml.parse(i, Xml.Encoding.UTF_8, root.getContentHandler());
        }
        catch (Exception e) {
            latest_forecast = new ForecastForDate();
            latest_forecast.start = new Date();
            latest_forecast.forecast = "Unable to get forecast: " + e.toString();

            forecasts = new ArrayList();
            forecasts.add(latest_forecast);
        }
        have_forecast = true;
    }

    public String getForecastDay(int index) {
        return new SimpleDateFormat("EEEE").format(forecasts.get(index).start);
    }

    public String getForecast(int index) {
        return forecasts.get(index).forecast;
    }

    public int getSize() {
        if (have_forecast) {
            return forecasts.size();
        } else {
            return 1;
        }
    }

    public boolean haveForecast() {
        return have_forecast;
    }

    private String getValue(Attributes attrs, String id) {
        String value = attrs.getValue(id);
        if (value == null) {
            value = "";
        }
        return value;
    }

    private static class ForecastForDate {
        public Date start;
        public String forecast;
    }

}
// vim: ts=4 sw=4 et
