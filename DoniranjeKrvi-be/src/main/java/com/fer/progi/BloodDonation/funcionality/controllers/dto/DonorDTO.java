package com.fer.progi.BloodDonation.funcionality.controllers.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class DonorDTO {


    private String username;

    private Date dateOfBirth;

    private String gender;

    private String bloodType;

    private String city;

    private boolean verified;

    private String firstName;

    private String lastName;

    private String phoneNumber;


    public DonorDTO(String username, Date dateOfBirth, String gender, String bloodType, String city, boolean verified, String firstName, String lastName, String phoneNumber) {
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.city = city;
        this.verified = verified;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

}
