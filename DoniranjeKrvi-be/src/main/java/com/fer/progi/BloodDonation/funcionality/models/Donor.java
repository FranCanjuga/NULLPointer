package com.fer.progi.BloodDonation.funcionality.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "donor")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Donor {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long donor_id;

        @Column(unique = true)
        private String username;

        @Column(name = "date_of_birth")
        private Date dateOfBirth;

        private String gender;

        @ManyToOne
        @JoinColumn(name = "blood_type_id")
        private BloodType bloodType;

        @ManyToOne
        @JoinColumn(name = "location_id")
        private Location location;

        private boolean verified;

        @OneToOne
        @JoinColumn(name = "user_username")
        private AppUser appUser;




        @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<DonationHistory> donationHistory = new HashSet<>();


/*
        @Column(unique = true)
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "donor_priznanje_junction",
                joinColumns = {@JoinColumn(name = "donorID")},
                inverseJoinColumns = {@JoinColumn(name = "priznanjeId")}
        )
        private Set<Priznanje> recognitions;
*/



        public Donor( String username, Date dateOfBirth, String gender, BloodType bloodType, Location location, boolean verified) {
                this.username = username;
                this.dateOfBirth = dateOfBirth;
                this.gender = gender;
                this.bloodType = bloodType;
                this.location = location;
                this.verified = verified;
                donationHistory = new HashSet<>();
        }

        public Donor(String username, Date dateOfBirth, String gender, BloodType bloodType, Location location) {
               this(username, dateOfBirth, gender, bloodType, location, false);
        }



}
