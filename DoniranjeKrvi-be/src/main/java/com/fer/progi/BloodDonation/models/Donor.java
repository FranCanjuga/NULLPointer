package com.fer.progi.BloodDonation.models;


import com.fer.progi.BloodDonation.security.models.AppUser;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity(name = "donor")
public class Donor {

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

        @OneToMany(mappedBy = "donor")
        private Set<DonationHistory> donationHistory;

        @OneToOne(mappedBy = "donor", cascade = CascadeType.ALL)
        private AppUser appUser;

        public Donor(String username, Date dateOfBirth, String gender, String bloodType, String town, String street, boolean verified, Set<DonationHistory> donationHistory) {
                this.username = username;
                this.dateOfBirth = dateOfBirth;
                this.gender = gender;
                this.bloodType = bloodType;
                this.town = town;
                this.street = street;
                this.verified = verified;
                this.donationHistory = donationHistory;
        }

        public Donor(String username, Date dateOfBirth, String gender, String bloodType, String town, String street, Set<DonationHistory> donationHistory) {
               this(username, dateOfBirth, gender, bloodType, town, street, false, donationHistory);
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

        public void setAppUser(AppUser appUser) {
                this.appUser = appUser;
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

        public AppUser getAppUser() {
                return appUser;
        }
}
