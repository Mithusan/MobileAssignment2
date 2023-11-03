package com.example.mobileassignment2;

public class location {
    private double latitude;
    private double longitude;

    public location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

