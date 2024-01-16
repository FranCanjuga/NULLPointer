package com.fer.progi.BloodDonation.funcionality.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DeleteAppointmentDTO {

    private String username;

    private Long appointmentID;

    public DeleteAppointmentDTO(String username, Long appointmentID) {
        this.username = username;
        this.appointmentID = appointmentID;
    }
}
