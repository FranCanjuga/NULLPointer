package com.fer.progi.BloodDonation.funcionality.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
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
        @UniqueConstraint(columnNames = {"priznanje_id", "donationHistory_id"})
})
@Entity(name = "priznanje_donora")
public class PriznanjaDonora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "priznanje_id")
    private Priznanje priznanje;

    @ManyToOne
    @JoinColumn(name = "donationHistory_id")
    private DonationHistory donationHistory;

}