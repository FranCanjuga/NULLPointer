package com.fer.progi.BloodDonation.funcionality.controllers.dto;

import com.fer.progi.BloodDonation.funcionality.models.AkcijaKrv;
import com.fer.progi.BloodDonation.funcionality.models.Appointment;
import com.fer.progi.BloodDonation.funcionality.models.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
public class AppointmentsResponseDTO {

    private Long appointment_id;

    private Location location;


    private String[] bloodTypes;

    private boolean criticalAction;

    private LocalDateTime dateAndTime;

    private boolean finished;


    public AppointmentsResponseDTO(Long appointment_id, Location location, String[] bloodTypes, boolean criticalAction, LocalDateTime dateAndTime, boolean finished) {
        this.appointment_id = appointment_id;
        this.location = location;
        this.bloodTypes = bloodTypes;
        this.criticalAction = criticalAction;
        this.dateAndTime = dateAndTime;
        this.finished = finished;
    }

    public AppointmentsResponseDTO(Appointment appointment) {
        this.appointment_id = appointment.getAppointment_id();
        this.location = appointment.getLocation();
        if(!appointment.getBloodTypes().isEmpty())
            this.bloodTypes = appointment.getBloodTypes().stream().map(akcijaKrv -> akcijaKrv.getBloodType().getType()).toArray(String[]::new);
        else
            this.bloodTypes = new String[0];
        this.criticalAction = appointment.isCriticalAction();
        this.dateAndTime =  appointment.getDateAndTime();
        this.finished = appointment.isFinished();
    }
}
