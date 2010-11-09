package com.emailross.baywinds;

public enum Location {
    PORT_PHILLIP    ("Port Phillip Bay",
                     "ftp://ftp2.bom.gov.au/anon/gen/fwo/IDV10460.xml",
                     "ftp://ftp2.bom.gov.au/anon/gen/fwo/IDV60034.html",
                     new String[]{"Fawkner Beacon", "Frankston"}
                    );

    private String name;
    private String forecast_uri;
    private String observation_uri;
    private String[] locations;

    Location(String name, String forecast_uri, String observation_uri, String[] locations) {
        this.name = name;
        this.forecast_uri = forecast_uri;
        this.observation_uri = observation_uri;
        this.locations = locations;
    }

    public String getName() {
        return name;
    }

    public String getForecastURI() {
        return forecast_uri;
    }

    public String getObservationURI() {
        return observation_uri;
    }

    public String[] getLocations() {
        return locations;
    }
}
// vim: ts=4 sw=4 et
