package com.fer.progi.BloodDonation.funcionality.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "donor")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Donor {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long donorID;

        @Column(unique = true)
        private String username;

        private Date dateOfBirth;

        private String gender;

        private String bloodType;

        private String town;

        private String street;

        private boolean verified;

        @OneToOne
        @JoinColumn(name = "user_username")
        private AppUser appUser;


        @Column(unique = true)
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "DonoroPriznanje",
                joinColumns = {@JoinColumn(name = "donorId")},
                inverseJoinColumns = {@JoinColumn(name = "priznanjeId")},
                uniqueConstraints = @UniqueConstraint(columnNames = {"donorId", "priznanjeId"})
        )
        private Set<Priznanje> priznanja;



        @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<DonationHistory> donationHistory = new HashSet<>();



        @Column(unique = true)
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "donor_priznanje_junction",
                joinColumns = {@JoinColumn(name = "donorID")},
                inverseJoinColumns = {@JoinColumn(name = "priznanjeId")}
        )
        private Set<Priznanje> recognitions;




        public Donor( String username, Date dateOfBirth, String gender, String bloodType, String town, String street, boolean verified) {
                this.username = username;
                this.dateOfBirth = dateOfBirth;
                this.gender = gender;
                this.bloodType = bloodType;
                this.town = town;
                this.street = street;
                this.verified = verified;
                donationHistory = new HashSet<>();
        }

        public Donor(String username, Date dateOfBirth, String gender, String bloodType, String town, String street) {
               this(username, dateOfBirth, gender, bloodType, town, street, false);
        }



}
