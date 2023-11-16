package com.fer.progi.BloodDonation.funcionality.models;

import jakarta.persistence.*;

@Entity
public class DonationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public Long getId() {
        return id;
    }

    public Donor getDonor() {
        return donor;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public boolean isExecuted() {
        return executed;
    }
}





