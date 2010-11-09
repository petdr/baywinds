package com.emailross.baywinds;

import java.util.*;

/**
 * The current observations of port phillip bay
 */
public class Observations {
    private String observations_uri = "ftp://ftp2.bom.gov.au/anon/gen/fwo/IDV60034.html";
    private List<Observation> observations;

    public Observations() {
        // XXX Look up the observations_uri to determine the data and insert it
        // into the list.
        observations = new ArrayList();

        Observation o;
        o = new Observation("Fawkner Beacon", "10", "12", "E");
        observations.add(o);

        o = new Observation("Frankston", "11", "14", "E");
        observations.add(o);

        o = new Observation("Short", "11", "14", "E");
        observations.add(o);

    }

    public List<Observation> getObservations() {
        return observations;
    }
}
// vim: ts=4 sw=4 et
