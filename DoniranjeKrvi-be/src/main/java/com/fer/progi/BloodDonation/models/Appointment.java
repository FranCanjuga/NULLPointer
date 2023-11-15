package com.fer.progi.BloodDonation.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "donationAppointment")
    public class Appointment {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long appointmentID;


        @ManyToOne
        @JoinColumn(name = "locationID")
        private Location location;

    @OneToMany(mappedBy = "appointment")
    private Set<DonationHistory> donors;

    private LocalDateTime dateAndTime;


}
