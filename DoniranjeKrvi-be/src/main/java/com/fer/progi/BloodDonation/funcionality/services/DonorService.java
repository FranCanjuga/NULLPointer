package com.fer.progi.BloodDonation.funcionality.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonationHistoryDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.ApointmentDTO;
import com.fer.progi.BloodDonation.funcionality.models.Appointment;
import com.fer.progi.BloodDonation.funcionality.models.DonationHistory;
import com.fer.progi.BloodDonation.funcionality.repositorys.DonorRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.DonationHistoryRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.AppointmentRepository;
import com.fer.progi.BloodDonation.funcionality.models.Donor;
import com.fer.progi.BloodDonation.funcionality.services.Exceptions.DonationReservationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DonorService {

    private final DonorRepository donorRepository;
    private final DonationHistoryRepository historyRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public DonorService(DonorRepository donorRepository, DonationHistoryRepository historyRepository, AppointmentRepository appointmentRepository) {

        this.donorRepository = donorRepository;
        this.historyRepository = historyRepository;
        this.appointmentRepository = appointmentRepository;
    }


    public DonorDTO getDonorDataByUsername(String username) {
        Optional<Donor> opt =  donorRepository.findDonorByUsername(username);

        if(opt.isEmpty()){
            return null;
        }
        Donor donor = opt.get();

        return  new DonorDTO(donor.getUsername() , donor.getDateOfBirth(), donor.getGender(), donor.getBloodType(), donor.getLocation().getLocationName(), donor.isVerified(),
                donor.getAppUser().getFirstName(), donor.getAppUser().getLastName(), donor.getAppUser().getPhoneNumber());
    }


    public DonationHistory createNewReservation(DonationHistoryDTO historyDTO) {
        Optional<Donor> opt  =  donorRepository.findDonorByUsername(historyDTO.getUsername());
        if(opt.isEmpty()){
            return null;
        }
        Donor donor = opt.get();

        Optional<Appointment> opt2  =  appointmentRepository.findAppointmentByAppointmentID(historyDTO.getAppointmentID());
        if(opt2.isEmpty()){
            return null;
        }
        Appointment appointment= opt2.get();

        DonationHistory history = new DonationHistory(donor, appointment, false);

        historyRepository.save(history);
        return history;
    }

    public DonationHistoryDTO getDonationReservationByUsername(String username) {

        Optional<Donor> opt =  donorRepository.findDonorByUsername(username);
        if(opt.isEmpty()){
            return null;
        }
        Donor donor = opt.get();

        Optional<DonationHistory> opt2 =historyRepository.findDonationHistoryByUsername(username);
        if(opt2.isEmpty()){
            return null;
        }
        DonationHistory donationHistory = opt2.get();

        Optional<Appointment> opt3  =  appointmentRepository.findAppointmentByAppointmentID(donationHistory.getAppointment().getAppointmentID());
        if(opt3.isEmpty()){
            return null;
        }
        Appointment appointment= opt3.get();

        //vraca sve rezervacije bez filtera
        DonationHistoryDTO allReservations =  new DonationHistoryDTO(username, appointment.getAppointmentID(), false);

        if(allReservations.getUsername().equals(username)
                && appointment.getDateAndTime().isAfter(LocalDateTime.now())) {
            return new DonationHistoryDTO(username, opt2.get().getAppointment().getAppointmentID());
        }
        else{
            throw new DonationReservationException("Donation reservation is not completed or has an error.");
        }
    }


    public List<Appointment> getListOfActiveDonationDates(String username) {

        return appointmentRepository.findAll();
    }
}
