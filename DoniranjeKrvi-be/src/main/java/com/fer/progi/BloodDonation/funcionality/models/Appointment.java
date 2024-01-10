package com.fer.progi.BloodDonation.funcionality.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "appointment")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
    public class Appointment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long appointmentID;


        @ManyToOne
        @JoinColumn(name = "locationID")
        private Location location;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DonationHistory> donationHistory = new HashSet<>();

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AkcijaKrv> bloodTypes = new HashSet<>();


    private boolean criticalAction;

    private LocalDateTime dateAndTime;

    private boolean finished;


}
