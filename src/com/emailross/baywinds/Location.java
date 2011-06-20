package com.emailross.baywinds;

public enum Location {
    /*
    PORT_PHILLIP    ("Port Phillip Bay",
                     "ftp://ftp2.bom.gov.au/anon/gen/fwo/IDV10460.xml", // Forecast
                     "ftp://ftp2.bom.gov.au/anon/gen/fwo/IDV60034.html", // All Melbourne observations
                     new String[]{"Fawkner Beacon", "Frankston"}
                    );
    */

    FAWKNER_BEACON  ("Fawkner Beacon",
                     "http://www.bom.gov.au/fwo/IDV60901/IDV60901.95872.json"
                    );


    private String name;
    private String observations_uri;

    Location(String name, String observations_uri) {
        this.name = name;
        this.observations_uri = observations_uri;
    }

    public String getName() {
        return name;
    }

    public String getObservationsURI() {
        return observations_uri;
    }
}
// vim: ts=4 sw=4 et
