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
        private Long appointment_id;


        @ManyToOne
        @JoinColumn(name = "location_id")
        private Location location;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DonationHistory> donationHistory = new HashSet<>();

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AkcijaKrv> bloodTypes = new HashSet<>();


    @Column(name = "critical_action")
    private boolean criticalAction;

    @Column(name = "date_and_time")
    private LocalDateTime dateAndTime;

    private boolean finished;


}
