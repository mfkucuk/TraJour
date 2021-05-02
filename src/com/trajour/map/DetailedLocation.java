package com.trajour.map;

import org.controlsfx.control.WorldMapView;

public class DetailedLocation extends WorldMapView.Location {
    private String countryName;
    private int population;

    public DetailedLocation(double latitude, double longitude) {
        super(latitude, longitude);
    }

    public DetailedLocation(String name, double latitude, double longitude) {
        super(name, latitude, longitude);
    }

    public DetailedLocation(String cityName, double latitude, double longitude, String countryName,  int population) {
        super(cityName, latitude, longitude);
        this.countryName = countryName;
        this.population = population;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getPopulation() {
        return population;
    }
}
