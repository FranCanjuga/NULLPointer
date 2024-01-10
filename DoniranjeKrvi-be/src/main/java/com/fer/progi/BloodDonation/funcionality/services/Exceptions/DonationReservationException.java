package com.fer.progi.BloodDonation.funcionality.services.Exceptions;

public class DonationReservationException extends RuntimeException {

    public DonationReservationException(String message) {
        super(message);
    }

    public DonationReservationException(String message, Throwable cause) {
        super(message, cause);
    }
}
