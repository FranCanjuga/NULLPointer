package com.fer.progi.BloodDonation.funcionality.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "blood_type")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BloodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int blood_type_id;

    @Column(unique = true)
    private String type;

    public BloodType(String type) {
        this.type = type;
    }
}
