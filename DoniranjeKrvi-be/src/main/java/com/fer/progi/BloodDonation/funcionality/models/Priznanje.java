package com.fer.progi.BloodDonation.funcionality.models;

import jakarta.persistence.*;
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
    private int priznanje_id;

    @Column(name = "name_priznanje")
    private String namePriznanje;

    private int condition;


}
