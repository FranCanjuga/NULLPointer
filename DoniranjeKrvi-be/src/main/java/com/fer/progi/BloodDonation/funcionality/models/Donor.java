package com.fer.progi.BloodDonation.funcionality.models;


import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "donor")
public class Donor   {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
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

        @OneToMany(mappedBy = "donor")
        private Set<DonationHistory> donationHistory;



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

        public AppUser getAppUser() {
                return appUser;
        }

        public Set<DonationHistory> getDonationHistory() {
                return donationHistory;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public void setDateOfBirth(Date dateOfBirth) {
                this.dateOfBirth = dateOfBirth;
        }

        public void setGender(String gender) {
                this.gender = gender;
        }

        public void setBloodType(String bloodType) {
                this.bloodType = bloodType;
        }

        public void setTown(String town) {
                this.town = town;
        }

        public void setStreet(String address) {
                this.street = address;
        }

        public void setVerified(boolean verified) {
                this.verified = verified;
        }



        public Long getDonorID() {
                return donorID;
        }

        public String getUsername() {
                return username;
        }

        public Date getDateOfBirth() {
                return dateOfBirth;
        }

        public String getGender() {
                return gender;
        }

        public String getBloodType() {
                return bloodType;
        }

        public String getTown() {
                return town;
        }

        public String getStreet() {
                return street;
        }

        public boolean isVerified() {
                return verified;
        }


}
