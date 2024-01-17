package com.fer.progi.BloodDonation.funcionality.controllers.dto;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReservationDTO {
    private final String username;

    private final Long appointment_id;

    private final Long[] potvrda;

    public String getUsername() {
        return username;
    }

    public Long getAppointmentID() {
        return appointment_id;
    }

    public Long[] getPotvrdeID() {
        return potvrda;
    }
}
