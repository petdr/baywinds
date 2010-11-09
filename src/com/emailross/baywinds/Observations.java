package com.emailross.baywinds;

import java.util.*;

/**
 * The current observations of port phillip bay
 */
public class Observations {
    private Location location;
    private List<Observation> observations;

    public Observations(Location l) {
        // XXX Look up the observations_uri and then use the locations
        // to initialize the list of locations
        location = l;

        observations = new ArrayList();
        Observation o;
        for (String name: location.getLocations()) {
            o = new Observation(name, "10", "12", "E");
            observations.add(o);
        }
    }

    public List<Observation> getObservations() {
        return observations;
    }
}
// vim: ts=4 sw=4 et
