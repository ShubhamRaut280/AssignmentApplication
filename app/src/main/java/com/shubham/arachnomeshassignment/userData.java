package com.shubham.arachnomeshassignment;

import org.parceler.Parcel;

@Parcel
public class userData  {
    String name;
    String userID;
    String contact;
    double latitude;
    double longitude;

    public userData() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public userData(String userID, String name, String contact, double latitude, double longitude) {
       this.userID = userID;
        this.name = name;
        this.contact = contact;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
