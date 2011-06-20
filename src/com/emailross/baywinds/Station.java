package com.emailross.baywinds;

/**
 * A weather station in Australia.
 */
public enum Station {
    FAWKNER_BEACON  ("Fawkner Beacon",
                     "http://www.bom.gov.au/fwo/IDV60901/IDV60901.95872.json"
                    ),
    ST_KILDA_RMYS   ("St Kilda RMYS",
                     "http://www.bom.gov.au/fwo/IDV60901/IDV60901.95864.json"
                    ),
    FRANKSTON       ("Frankston",
                     "http://www.bom.gov.au/fwo/IDV60901/IDV60901.94871.json"
                    ),
    SOUTH_CHANNEL   ("South Channel Island",
                     "http://www.bom.gov.au/fwo/IDV60901/IDV60901.94853.json"
                    ),
    POINT_WILSON    ("Point Wilson",
                     "http://www.bom.gov.au/fwo/IDV60901/IDV60901.94847.json"
                    );

    private String name;
    private String observations_uri;

    Station(String name, String observations_uri) {
        this.name = name;
        this.observations_uri = observations_uri;
    }

    /**
     * The name of this weather station
     */
    public String getName() {
        return name;
    }

    /**
     * The URI which contains a JSON description of the latest observations at
     * this weather station
     */
    public String getObservationsURI() {
        return observations_uri;
    }
}
// vim: ts=4 sw=4 et

