package com.emailross.ozwinds;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector.SimpleOnGestureListener;

import java.util.ArrayList;

import android.util.Log;

import com.adwhirl.*;

public class BayWinds extends ListActivity
{
    private Location location;
    private Forecast forecast;
    private Observations observations;

    private SharedPreferences prefs;

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

        prefs = getPreferences(MODE_PRIVATE);

        // Restore state from bundle if it exists
        if (savedInstanceState != null) {
            Log.d("BayWinds", "restoring state");
            location = (Location) savedInstanceState.getSerializable(LOCATION_KEY);
            forecast = (Forecast) savedInstanceState.getSerializable(FORECAST_KEY);
            observations = (Observations) savedInstanceState.getSerializable(OBSERVATIONS_KEY);
        } else {
            int location_ordinal = prefs.getInt(LOCATION_KEY, Location.PORT_PHILLIP.ordinal());
            location = Location.values()[location_ordinal];
            forecast = new Forecast(location);
            observations = new Observations(location);
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

        // Create the ad
        LinearLayout layout = (LinearLayout)findViewById(R.id.mainLayout);

        AdWhirlLayout adWhirlLayout = new AdWhirlLayout(this, "7b8e10ac47c949ec8acb883a43f44026");
        adWhirlLayout.setGravity(Gravity.BOTTOM);
        RelativeLayout.LayoutParams adWhirlLayoutParams = new RelativeLayout.LayoutParams(320, 52);
        layout.addView(adWhirlLayout, adWhirlLayoutParams);

        layout.invalidate();
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
        observations.setLocation(l);

        Editor e = prefs.edit();
        e.putInt(LOCATION_KEY, l.ordinal());
        e.commit();
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
        new GetObservationsTask().execute(observations);
    }

    public void displayForecast() {
        TextView f = (TextView) findViewById(R.id.forecast);
        f.setText("Forecast for " + forecast.getForecastDay(0) + "\n" + forecast.getForecast(0));

        final GestureDetector gd = new GestureDetector(new ForecastGestureListener(f));
        View.OnTouchListener gl = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gd.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        };

        f.setOnTouchListener(gl);
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

    private class GetObservationsTask extends AsyncTask<Observations, Void, Integer>
    {
      	protected Integer doInBackground(Observations... args) {
            args[0].refresh();
            return new Integer(1);
        }

        protected void onPostExecute(Integer i) {
            displayObservations();
        }
	}

    private class ForecastGestureListener extends GestureDetector.SimpleOnGestureListener {
        private TextView text_view;
        private int index = 0;

        public ForecastGestureListener(TextView tv) {
            text_view = tv;
        }

        // This is needed otherwise the fling event cannot be detected.
        public boolean onDown(MotionEvent e) {
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (velocityX > 0) {
                index--;
                if (index < 0) {
                    index = 0;
                }
            } else {
                index++;
                if (index >= forecast.getSize()) {
                    index = forecast.getSize() - 1;
                }
            }
            text_view.setText("Forecast for " + forecast.getForecastDay(index) + "\n" + forecast.getForecast(index));
            return true;
        }
    }

}
// vim: ts=4 sw=4 et
