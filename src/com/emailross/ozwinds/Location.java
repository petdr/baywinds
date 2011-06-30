package com.emailross.ozwinds;

/**
 * A Location for which the application will provide a forecast and wind readings
 * from a various set of weather stations at the location.
 */
public enum Location {
    PORT_PHILLIP    ("Port Phillip Bay",
                     "ftp://ftp2.bom.gov.au/anon/gen/fwo/IDV10460.xml",
                     new Station[] {Station.FAWKNER_BEACON, Station.ST_KILDA_RMYS, Station.FRANKSTON, Station.SOUTH_CHANNEL, Station.POINT_WILSON}
                    );


    private String name;
    private String forecast_uri;
    private Station[] stations;

    Location(String name, String forecast_uri, Station[] stations) {
        this.name = name;
        this.forecast_uri = forecast_uri;
        this.stations = stations;
    }

    public String getName() {
        return name;
    }

    public String getForecastUri() {
        return forecast_uri;
    }

    public Station[] getStations() {
        return stations;
    }
}
// vim: ts=4 sw=4 et
