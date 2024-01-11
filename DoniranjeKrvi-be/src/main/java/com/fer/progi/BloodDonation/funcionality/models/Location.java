package com.fer.progi.BloodDonation.funcionality.models;


import jakarta.persistence.*;

import lombok.*;

@Entity(name = "donationLocation")
@Setter
@Getter
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationID;

    @Column(unique = true)
    private String locationName;

    private double longitude;

    private double latitude;

    public Location(Long locationID, String locationName, double longitude, double latitude) {
        this.locationID = locationID;
        this.locationName = locationName;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
