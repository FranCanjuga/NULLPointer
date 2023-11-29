package com.fer.progi.BloodDonation.funcionality.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "priznanje")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Priznanje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int priznanjeId;

    private String namePriznanje;

    private int condition;


}
