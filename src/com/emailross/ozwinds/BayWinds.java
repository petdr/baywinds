package com.emailross.ozwinds;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;

import java.util.ArrayList;

import android.util.Log;

import com.google.ads.*;

public class BayWinds extends ListActivity
{
    private Location location;
    private Forecast forecast;
    private Observations observations;

    private static String LOCATION_KEY = "location";
    private static String FORECAST_KEY = "forecast";
    private static String OBSERVATIONS_KEY = "observations";

    private static final int MENU_ITEM_REFRESH = Menu.FIRST;
    private static final int MENU_ITEM_LOCATION = MENU_ITEM_REFRESH + 1;

    private static final int DIALOG_SELECT_LOCATION = Menu.FIRST;

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

        // Create the adView
        AdView adView = (AdView)this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest());
  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ITEM_REFRESH, 0, R.string.menu_refresh);
        menu.add(0, MENU_ITEM_LOCATION, 0, R.string.menu_location);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ITEM_REFRESH:
                refresh();
                return true;
            case MENU_ITEM_LOCATION:
                showDialog(DIALOG_SELECT_LOCATION);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Dialog onCreateDialog(int d) {
        switch (d) {
            case DIALOG_SELECT_LOCATION:
                // Construct the list of locations to choose from
                ArrayList<CharSequence> list = new ArrayList();
                for (Location l: Location.values()) {
                    list.add(l.getName());
                }
                final CharSequence[] items = (CharSequence[]) list.toArray(new CharSequence[0]);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Pick a location");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        setLocation(Location.values()[item]);
                        refresh();
                    }
                });
                AlertDialog alert = builder.create();
                return alert;
        }

        return null;
    }

    private void setLocation(Location l) {
        this.location = l;
    }

    public void refresh() {
        refreshForecast();
        refreshObservations();
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
