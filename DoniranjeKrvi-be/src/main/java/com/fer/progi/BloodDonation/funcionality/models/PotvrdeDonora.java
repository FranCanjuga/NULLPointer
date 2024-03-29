package com.fer.progi.BloodDonation.funcionality.models;

import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"potvrda_id", "donation_history_id"})
})
@Entity(name = "potvrde_donora")
public class PotvrdeDonora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @ManyToOne
    @JoinColumn(name = "potvrda_id")
    private Potvrda potvrda;

    @ManyToOne
    @JoinColumn(name = "donation_history_id")
    private DonationHistory donationHistory;

    private Date expiers;

    private Boolean given;

    public PotvrdeDonora(Potvrda potvrda, DonationHistory donationHistory, Date expiers, boolean given) {
        this.potvrda = potvrda;
        this.donationHistory = donationHistory;
        this.expiers = expiers;
        this.given = given;
    }
}