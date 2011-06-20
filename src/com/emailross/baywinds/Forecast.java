package com.emailross.baywinds;

import java.net.*;
import java.io.*;

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
    private String forecast_today;
    private transient String aac;
    private transient String index;

    public Forecast(Location location) {
        try {
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
                    f.index = getValue(attrs, "index");
                    Log.w("BayWinds", f.index);
                }
            });
            text.setEndTextElementListener(new EndTextElementListener() {
                public void end(String body) {
                    if (f.aac.equals("VIC_MW005") && f.index.equals("0")) {
                        f.forecast_today = body.replace(" Seas: ", "\n\nSeas:\n").replace("Winds: ", "Winds:\n") + "\n";
                    }
                }
            });

            Xml.parse(i, Xml.Encoding.UTF_8, root.getContentHandler());
        }
        catch (Exception e) {
            forecast_today = "Unable to get forecast: " + e.toString();
        }
    }

    public String getValue(Attributes attrs, String id) {
        String value = attrs.getValue(id);
        if (value == null) {
            value = "";
        }
        return value;
    }

    public String getTodaysForecast() {
        return forecast_today;
    }
}
// vim: ts=4 sw=4 et
