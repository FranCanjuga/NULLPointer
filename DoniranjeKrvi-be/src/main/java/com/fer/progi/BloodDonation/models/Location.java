package com.fer.progi.BloodDonation.models;


import jakarta.persistence.*;

@Entity(name = "donationLocation")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long locationID;

    private String locationName;

    private String town;

    private String street;

    private String phoneNumber;

    private boolean active;

    public Location(String locationName, String town, String street, String phoneNumber, boolean active) {
        this.locationName = locationName;
        this.town = town;
        this.street = street;
        this.phoneNumber = phoneNumber;
        this.active = active;
    }

    public Location(String locationName, String town, String street, boolean active) {
        this(locationName,town,street,null,active);
    }

    public Location(String locationName, String town, String street) {
        this(locationName, town, street,null,true);
    }

    public Location( String town, String street) {
        this(null, town, street,null,true);
    }

    public void setLocationID(Long locationID) {
        this.locationID = locationID;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getLocationID() {
        return locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getTown() {
        return town;
    }

    public String getStreet() {
        return street;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isActive() {
        return active;
    }
}
