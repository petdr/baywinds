package com.emailross.ozwinds;

/**
 * A Location for which the application will provide a forecast and wind readings
 * from a various set of weather stations at the location.
 */
public enum Location {
    PORT_PHILLIP    ("Port Phillip Bay",
                     "ftp://ftp2.bom.gov.au/anon/gen/fwo/IDV10460.xml",
                     "VIC_MW005",
                     new Station[] {Station.FAWKNER_BEACON, Station.ST_KILDA_RMYS, Station.FRANKSTON, Station.SOUTH_CHANNEL, Station.POINT_WILSON}
                    ),
    SYDNEY_HARBOUR  ("Sydney Harbour",
                     "ftp://ftp2.bom.gov.au/anon/gen/fwo/IDN11013.xml",
                     "NSW_MW009",
                     new Station[] {Station.NORTH_HEAD, Station.SYDNEY_HARBOUR, Station.FORT_DENISON, Station.LITTLE_BAY, Station.KURNELL}
                    );

    private String name;
    private String forecast_uri;
    private String forecast_area;
    private Station[] stations;

    Location(String name, String forecast_uri, String forecast_area, Station[] stations) {
        this.name = name;
        this.forecast_uri = forecast_uri;
        this.forecast_area = forecast_area;
        this.stations = stations;
    }

    public String getName() {
        return name;
    }

    public String getForecastUri() {
        return forecast_uri;
    }

    public String getArea() {
        return forecast_area;
    }

    public Station[] getStations() {
        return stations;
    }
}
// vim: ts=4 sw=4 et
