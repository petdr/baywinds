package com.emailross.baywinds;

import java.io.Serializable;

public class Observation implements Serializable {
    private String name, wind_strength, gust_strength, wind_direction;

    public Observation(String name, String wind_strength, String gust_strength, String wind_direction) {
        this.name = name;
        this.wind_strength = wind_strength;
        this.gust_strength = gust_strength;
        this.wind_direction = wind_direction;
    }

    public String getName() {
        return name;
    }

    public String getWindStrength() {
        return wind_strength;
    }

    public String getGustStrength() {
        return gust_strength;
    }

    public String getWindDirection() {
        return wind_direction;
    }
}
