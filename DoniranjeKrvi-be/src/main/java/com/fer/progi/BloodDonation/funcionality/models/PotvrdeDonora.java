package com.fer.progi.BloodDonation.funcionality.models;

import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"potvrdaId", "donationHistoryId"})
})
@Entity
public class PotvrdeDonora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @ManyToOne
    @JoinColumn(name = "potvrdaId")
    private Potvrda potvrda;

    @ManyToOne
    @JoinColumn(name = "donationHistoryId")
    private DonationHistory donationHistory;

    private Date expiers;

    private boolean given;

}