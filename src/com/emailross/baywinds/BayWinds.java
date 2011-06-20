package com.emailross.baywinds;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

// Latest BOM melbourne observations
// ftp://ftp2.bom.gov.au/anon/gen/fwo/IDV60034.html

// Latest Melbourne forecast
// ftp://ftp2.bom.gov.au/anon/gen/fwo/IDV10460.xml
public class BayWinds extends ListActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Location location = Location.FAWKNER_BEACON;

        Forecast forecast = new Forecast(location);

        TextView f = (TextView) findViewById(R.id.forecast);
        f.setText(forecast.getTodaysForecast());

        Observations observations = new Observations(location);
        ObservationAdapter adapter = new ObservationAdapter(this, observations.getObservations());
        setListAdapter(adapter);
    }
}
// vim: ts=4 sw=4 et
