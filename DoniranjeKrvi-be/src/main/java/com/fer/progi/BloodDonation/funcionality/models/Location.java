package com.fer.progi.BloodDonation.funcionality.models;


import jakarta.persistence.*;

import lombok.*;

@Entity(name = "donationLocation")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


}
