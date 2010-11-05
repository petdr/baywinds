package com.emailross.baywinds;

import android.app.Activity;
import android.os.Bundle;

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
    }
}
