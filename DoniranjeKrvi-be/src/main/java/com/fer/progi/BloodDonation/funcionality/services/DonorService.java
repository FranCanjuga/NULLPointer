package com.fer.progi.BloodDonation.funcionality.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonationHistoryDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.models.Appointment;
import com.fer.progi.BloodDonation.funcionality.models.DonationHistory;
import com.fer.progi.BloodDonation.funcionality.repositorys.DonorRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.DonationHistoryRepository;
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

    @Autowired
    public DonorService(DonorRepository donorRepository, DonationHistoryRepository historyRepository) {

        this.donorRepository = donorRepository;
        this.historyRepository = historyRepository;
    }


    public DonorDTO getDonorDataByUsername(String username) {
        Optional<Donor> opt =  donorRepository.findDonorByUsername(username);

        if(opt.isEmpty()){
            return null;
        }
        Donor donor = opt.get();
        return  new DonorDTO(donor.getUsername() , donor.getDateOfBirth(), donor.getGender(), donor.getBloodType(), donor.getTown(), donor.getStreet(), donor.isVerified(),
                donor.getAppUser().getFirstName(), donor.getAppUser().getLastName(), donor.getAppUser().getPhoneNumber());
    }

    public DonationHistory createNewReservation(String username, Appointment appointment){
        Optional<Donor> opt  =  donorRepository.findDonorByUsername(username);
        if(opt.isEmpty()){
            return null;
        }
        Donor donor = opt.get();

        return new DonationHistory(donor, appointment, false);
    }

    public DonationHistoryDTO getDonationReservationById(Long id) {
        //donorRepository.findById(id);
        //return donationReservationRepository.findById(id).orElse(null);
        return null;
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

        //vraca sve rezervacije bez filtera
        DonationHistoryDTO allReservations =  new DonationHistoryDTO(username, donationHistory.getAppointment(), false);

        if(allReservations.getUsername().equals(username)
                && allReservations.getAppointment().getDateAndTime().isAfter(LocalDateTime.now())) {
            return new DonationHistoryDTO(username, opt2.get().getAppointment(), false);
        }
        else{
            throw new DonationReservationException("Donation reservation is not completed or has an error.");
        }
    }
}
