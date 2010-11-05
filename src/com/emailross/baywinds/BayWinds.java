package com.emailross.baywinds;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

// Latest BOM melbourne observations
// ftp://ftp2.bom.gov.au/anon/gen/fwo/IDV60034.html

// Latest Melbourne forecast
// ftp://ftp2.bom.gov.au/anon/gen/fwo/IDV10460.xml
public class BayWinds extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
// vim: ts=4 sw=4 et
