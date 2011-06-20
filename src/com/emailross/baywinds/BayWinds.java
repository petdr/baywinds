package com.emailross.baywinds;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

import android.util.Log;

public class BayWinds extends ListActivity
{
    private Forecast forecast;
    private Observations observations;

    private static String FORECAST_KEY = "forecast";
    private static String OBSERVATIONS_KEY = "observations";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Restore state from bundle if it exists
        if (savedInstanceState != null) {
            Log.d("BayWinds", "restoring state");
            forecast = (Forecast) savedInstanceState.getSerializable(FORECAST_KEY);
            observations = (Observations) savedInstanceState.getSerializable(OBSERVATIONS_KEY);
        } else {
            forecast = null;
            observations = null;
        }

        // Recreate forecast and observstions if required
        if (forecast == null || observations == null) {
            Log.d("BayWinds", "loading state");
            Location location = Location.PORT_PHILLIP;

            forecast = new Forecast(location);
            observations = new Observations(location);
        }

        TextView f = (TextView) findViewById(R.id.forecast);
        f.setText(forecast.getTodaysForecast());

        ObservationAdapter adapter = new ObservationAdapter(this, observations.getObservations());
        setListAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("BayWinds", "saving state");
        outState.putSerializable(FORECAST_KEY, forecast);
        outState.putSerializable(OBSERVATIONS_KEY, observations);
    }
}
