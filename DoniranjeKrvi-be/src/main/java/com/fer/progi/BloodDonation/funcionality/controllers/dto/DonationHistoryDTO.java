package com.fer.progi.BloodDonation.funcionality.controllers.dto;

import com.fer.progi.BloodDonation.funcionality.models.Appointment;
import com.fer.progi.BloodDonation.funcionality.models.Donor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DonationHistoryDTO {

    private String username;

    private Appointment appointment;

    private boolean came;

    public DonationHistoryDTO(String username, Appointment appointment, boolean came) {
        this.username = username;
        this.appointment = appointment;
        this.came = came;
    }
}
