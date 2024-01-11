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

    private Long appointmentID;

    private boolean came;

    private Long[] potvrdeID;


    private Donor donor;
    private  Appointment appointment;

    public DonationHistoryDTO(boolean came, Donor donor, Appointment appointment) {
        this.came = came;
        this.donor = donor;
        this.appointment = appointment;
    }

    public DonationHistoryDTO(String username, Long appointmentID, boolean came, Long[] potvrdeID) {
        this.username = username;
        this.appointmentID = appointmentID;
        this.came = came;
        this.potvrdeID = potvrdeID;
    }

    public DonationHistoryDTO(String username, Long appointmentID, Long[] potvrdeID) {
        this.username = username;
        this.appointmentID = appointmentID;
        this.potvrdeID = potvrdeID;
    }

    public DonationHistoryDTO(String username, Long appointmentID, boolean came) {
        this.username = username;
        this.appointmentID = appointmentID;
        this.came = came;
    }

    public DonationHistoryDTO(String username, Long appointmentID) {
        this.username = username;
        this.appointmentID = appointmentID;
    }
}
