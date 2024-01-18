package com.fer.progi.BloodDonation.funcionality.controllers.dto;

import com.fer.progi.BloodDonation.funcionality.models.Appointment;
import com.fer.progi.BloodDonation.funcionality.models.DonationHistory;
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

    private String locationName;
    private String dateAndTime;
    private boolean finished;

    /**
     * Constructor for DonationHistoryDTO in method getDonationReservationByUsername in DonorService
     * for request GET /donor/donationReservation/{username}
     */
    public DonationHistoryDTO(DonationHistory donationHistory ,Appointment appointment, boolean came, boolean finished){
        this.appointmentID = appointment.getAppointment_id();
        this.came = came;
        this.dateAndTime = appointment.getDateAndTime().toString();
        this.locationName = appointment.getLocation().getLocationName();
        this.finished = finished;
        this.username =donationHistory.getDonor().getUsername();
    }




    public DonationHistoryDTO(String username, Long appointmentID, Long[] potvrdeID) {
        this.username = username;
        this.appointmentID = appointmentID;
        this.potvrdeID = potvrdeID;
    }
}
