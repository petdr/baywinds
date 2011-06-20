package com.emailross.baywinds;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

import android.util.Log;

public class BayWinds extends ListActivity
{
    private Location location;
    private Forecast forecast;
    private Observations observations;

    private static String LOCATION_KEY = "location";
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
            location = (Location) savedInstanceState.getSerializable(LOCATION_KEY);
            forecast = (Forecast) savedInstanceState.getSerializable(FORECAST_KEY);
            observations = (Observations) savedInstanceState.getSerializable(OBSERVATIONS_KEY);
        } else {
            location = Location.PORT_PHILLIP;
            forecast = new Forecast();
            observations = new Observations();
        }


        // Get the forecast and observations if we don't have them
        if (!forecast.haveForecast()) {
            refreshForecast();
        }

        if (!observations.haveObservations()) {
            refreshObservations();
        }

        displayForecast();
        displayObservations();
    }

    public void refreshForecast() {
        Log.d("BayWinds", "loading forecast");
        new GetForecastTask().execute(location);

    }

    public void refreshObservations() {
        Log.d("BayWinds", "loading observations");
        new GetObservationsTask().execute(location);
    }

    public void displayForecast() {
        TextView f = (TextView) findViewById(R.id.forecast);
        f.setText(forecast.getTodaysForecast());
    }

    public void displayObservations() {
        ObservationAdapter adapter = new ObservationAdapter(this, observations.getObservations());
        setListAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("BayWinds", "saving state");
        outState.putSerializable(LOCATION_KEY, location);
        outState.putSerializable(FORECAST_KEY, forecast);
        outState.putSerializable(OBSERVATIONS_KEY, observations);
    }

    private class GetForecastTask extends AsyncTask<Location, Void, Forecast>
    {
      	protected Forecast doInBackground(Location... args) {
            return new Forecast(args[0]);
        }

        protected void onPostExecute(Forecast forecast) {
            BayWinds.this.forecast = forecast;
            displayForecast();
        }
	}

    private class GetObservationsTask extends AsyncTask<Location, Void, Observations>
    {
      	protected Observations doInBackground(Location... args) {
            return new Observations(args[0]);
        }

        protected void onPostExecute(Observations observations) {
            BayWinds.this.observations = observations;
            displayObservations();
        }
	}
}
// vim: ts=4 sw=4 et
