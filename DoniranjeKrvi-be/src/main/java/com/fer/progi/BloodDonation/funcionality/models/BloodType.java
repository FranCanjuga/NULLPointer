package com.fer.progi.BloodDonation.funcionality.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "bloodType")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BloodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bloodTypeId;
    private String type;

    public BloodType(String type) {
        this.type = type;
    }
}
