package com.fer.progi.BloodDonation.funcionality.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "donationAppointment")
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

    @OneToMany(mappedBy = "appointment")
    private Set<DonationHistory> donors;

    private LocalDateTime dateAndTime;


}
