package com.fer.progi.BloodDonation.funcionality.services;

import com.fer.progi.BloodDonation.funcionality.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.*;

import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonationHistoryDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.ApointmentDTO;
import com.fer.progi.BloodDonation.funcionality.repositorys.DonorRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.DonationHistoryRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.AppointmentRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.LocationRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.BloodTypeRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.PotvrdaRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.PotvrdeDonoraRepository;
import com.fer.progi.BloodDonation.funcionality.services.Exceptions.DonationReservationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DonorService {

    private final DonorRepository donorRepository;
    private final DonationHistoryRepository historyRepository;
    private final AppointmentRepository appointmentRepository;
    private final LocationRepository locationRepository;
    private final BloodTypeRepository bloodTypeRepository;
    private final PotvrdaRepository potvrdaRepository;
    private final PotvrdeDonoraRepository potvrdeDonoraRepository;

    @Autowired
    public DonorService(DonorRepository donorRepository, DonationHistoryRepository historyRepository, AppointmentRepository appointmentRepository, LocationRepository locationRepository, BloodTypeRepository bloodTypeRepository, PotvrdaRepository potvrdaRepository, PotvrdeDonoraRepository potvrdeDonoraRepository) {

        this.donorRepository = donorRepository;
        this.historyRepository = historyRepository;
        this.appointmentRepository = appointmentRepository;
        this.locationRepository = locationRepository;
        this.bloodTypeRepository = bloodTypeRepository;
        this.potvrdaRepository = potvrdaRepository;
        this.potvrdeDonoraRepository = potvrdeDonoraRepository;
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


        Set<DonationHistory> donationHistorySet = donor.getDonationHistory();
        Appointment lastAppointment = null;
        for(DonationHistory d: donationHistorySet){
            assert lastAppointment != null;
            if (d.getAppointment().getDateAndTime().isAfter(lastAppointment.getDateAndTime() )){
                lastAppointment = d.getAppointment();
            }
        }

        /*List<Appointment> appointmentList = appointmentRepository.findAll();
        //Nalazi zadnje napravljeni appointment
        for (Appointment a : appointmentList) {
            if ((lastAppointment == null || a.getDateAndTime().isAfter(lastAppointment.getDateAndTime()))) {
               DonationHistory donationHistory = (DonationHistory) a.getDonationHistory();
               if(donationHistory.getDonor() == donor){
                   lastAppointment = a;
               }
            }
        }*/

        LocalDateTime appointmentDateTime = lastAppointment.getDateAndTime();
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
        if(appointmentDateTime.isAfter(threeMonthsAgo) ){
            //Prosla donacija je bila prije manje od 3 mjeseca, donor ne smije davati krv.
            return null;
        }
        else{
            DonationHistory history = new DonationHistory(donor, appointment, false);
            Long[] longPotvrda = historyDTO.getPotvrdeID();

            for(Long l: longPotvrda){
                PotvrdeDonora potvrdeDonora = new PotvrdeDonora(potvrdaRepository.findPotvrdaByPotvrdaId(l),history,null,false);
                potvrdeDonoraRepository.save(potvrdeDonora);
            }


            historyRepository.save(history);
            return history;
        }

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
            return new DonationHistoryDTO(false, donor, opt2.get().getAppointment());
        }
        else{
            return null;
        }
    }

    public List<Appointment> getListOfActiveDonationDates(String username) {

        return appointmentRepository.findAll();
    }

    public List<Location> getListOfLocations(String username) {
        return locationRepository.findAll();
    }

    public List<BloodType> getListOfBloodTypes(String username) {
        return bloodTypeRepository.findAll();
    }
}
