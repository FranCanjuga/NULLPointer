package com.fer.progi.BloodDonation.funcionality.controllers.dto;

import com.fer.progi.BloodDonation.funcionality.models.AkcijaKrv;
import com.fer.progi.BloodDonation.funcionality.models.Appointment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
public class AppointmentsResponseDTO {

    private Long appointment_id;

    private Long locationID ;

    private String locationName;


    private String[] bloodTypes;

    private boolean criticalAction;

    private LocalDateTime dateAndTime;

    private boolean finished;


    public AppointmentsResponseDTO(Long appointment_id, Long locationID, String[] bloodTypes, boolean criticalAction, LocalDateTime dateAndTime, boolean finished) {
        this.appointment_id = appointment_id;
        this.locationID = locationID;
        this.bloodTypes = bloodTypes;
        this.criticalAction = criticalAction;
        this.dateAndTime = dateAndTime;
        this.finished = finished;
    }

    public AppointmentsResponseDTO(Appointment appointment) {
        this.appointment_id = appointment.getAppointment_id();
        this.locationID = appointment.getLocation().getLocation_id();
        if(!appointment.getBloodTypes().isEmpty())
            this.bloodTypes = appointment.getBloodTypes().stream().map(akcijaKrv -> akcijaKrv.getBloodType().getType()).toArray(String[]::new);
        else
            this.bloodTypes = new String[0];
        this.criticalAction = appointment.isCriticalAction();
        this.dateAndTime =  appointment.getDateAndTime();
        this.finished = appointment.isFinished();
        this.locationName = appointment.getLocation().getLocationName();
    }
}
