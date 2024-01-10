package com.fer.progi.BloodDonation.funcionality.controllers.dto;

import com.fer.progi.BloodDonation.funcionality.models.Appointment;
import com.fer.progi.BloodDonation.funcionality.models.Donor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AppointmentFinishedDTO {

    private String[] usernames;

    private Long appointmentID;


    public AppointmentFinishedDTO(String[] usernames, Long appointmentId) {
        this.usernames = usernames;
        this.appointmentID = appointmentId;
    }


}
