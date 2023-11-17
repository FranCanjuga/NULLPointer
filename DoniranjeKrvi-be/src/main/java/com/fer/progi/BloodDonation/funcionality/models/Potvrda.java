package com.fer.progi.BloodDonation.funcionality.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "potvrda")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Potvrda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int potvrdaId;



    String namePotvrda;

    @OneToMany(mappedBy = "potvrda", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PotvrdeDonora> confirmationHistory = new HashSet<>();



}
