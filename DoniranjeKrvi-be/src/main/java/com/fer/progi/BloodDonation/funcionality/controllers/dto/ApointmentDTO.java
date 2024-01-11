package com.fer.progi.BloodDonation.funcionality.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * DTO class for requesting creation of new appointment.
 */
@NoArgsConstructor
@Getter
@Setter
public class ApointmentDTO {

    /** bloodTypes gleda se samo ako je critical action */
    public String[] bloodTypes;
    public Long locationID;
    public boolean criticalAction;
    public String dateAndTime;

    public ApointmentDTO(String[] bloodTypes, Long locationID, boolean criticalAction, String dateAndTime) {
        this.bloodTypes = bloodTypes;
        this.locationID = locationID;
        this.criticalAction = criticalAction;
        this.dateAndTime = dateAndTime;
    }
}
