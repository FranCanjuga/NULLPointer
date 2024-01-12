package com.fer.progi.BloodDonation.funcionality.models;


import jakarta.persistence.*;

import lombok.*;

@Entity(name = "donation_location")
@Setter
@Getter
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long location_id;

    @Column(unique = true)
    private String locationName;

    private double longitude;

    private double latitude;

    public Location(Long locationID, String locationName, double longitude, double latitude) {
        this.location_id = locationID;
        this.locationName = locationName;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
