package com.fer.progi.BloodDonation.funcionality.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"donorID", "appointmentID"})
})
public class DonationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationHistoryId;

    @ManyToOne
    @JoinColumn(name = "donorID")
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "appointmentID")
    private Appointment appointment;

    private boolean came;


    @OneToMany(mappedBy = "donationHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PotvrdeDonora> confirmationHistory = new HashSet<>();






    public DonationHistory(Donor donor, Appointment appointment, boolean executed) {
        this.donor = donor;
        this.appointment = appointment;
        this.came = executed;
    }

    public DonationHistory(Donor donor, Appointment appointment) {
        this(donor, appointment, false);
    }


}





