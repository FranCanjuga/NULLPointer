package com.fer.progi.BloodDonation.funcionality.controllers.dto;

import com.fer.progi.BloodDonation.funcionality.models.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter

public class AppointmentGetDTO {
    private Long appointment_id;
    private Location location;
    public LocalDateTime dateAndTime;

    public AppointmentGetDTO(Long appointment_id, Location location, LocalDateTime dateAndTime) {
        this.appointment_id = appointment_id;
        this.location = location;
        this.dateAndTime = dateAndTime;
    }
}
