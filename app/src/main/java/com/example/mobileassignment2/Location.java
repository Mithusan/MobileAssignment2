package com.example.mobileassignment2;

public class Location {
    private long id;
    private String latitude;
    private String longitude;
    private String address;

    Location(long id, String latitude, String longitude, String address) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }
    Location(String address, String latitude, String longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    Location(){

    }


    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLongitude(String longitude){
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }
}

