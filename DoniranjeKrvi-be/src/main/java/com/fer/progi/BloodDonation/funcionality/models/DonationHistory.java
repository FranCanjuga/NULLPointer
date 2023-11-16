package com.fer.progi.BloodDonation.funcionality.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DonationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donorID")
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "appointmentID")
    private Appointment appointment;

    private boolean executed;


    public DonationHistory(Donor donor, Appointment appointment, boolean executed) {
        this.donor = donor;
        this.appointment = appointment;
        this.executed = executed;
    }

    public DonationHistory(Donor donor, Appointment appointment) {
        this(donor, appointment, false);
    }


}





