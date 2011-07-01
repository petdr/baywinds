package com.emailross.ozwinds;

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
                    ),
    NORTH_HEAD      ("North Head",
                     "http://www.bom.gov.au/fwo/IDN60901/IDN60901.95768.json"
                    ),
    SYDNEY_HARBOUR  ("Sydney Harbour",
                     "http://www.bom.gov.au/fwo/IDN60901/IDN60901.95766.json"
                    ),
    FORT_DENISON    ("Fort Dension",
                     "http://www.bom.gov.au/fwo/IDN60901/IDN60901.94769.json"
                    ),
    LITTLE_BAY      ("Little Bay",
                     "http://www.bom.gov.au/fwo/IDN60901/IDN60901.94780.json"
                    ),
    KURNELL         ("Kurnell",
                     "http://www.bom.gov.au/fwo/IDN60901/IDN60901.95756.json"
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

