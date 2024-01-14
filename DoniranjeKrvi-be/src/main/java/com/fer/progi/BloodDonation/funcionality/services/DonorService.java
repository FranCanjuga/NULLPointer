package com.fer.progi.BloodDonation.funcionality.services;

import com.fer.progi.BloodDonation.funcionality.models.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonationHistoryDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.repositorys.DonorRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.DonationHistoryRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.AppointmentRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.LocationRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.BloodTypeRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.PotvrdaRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.PotvrdeDonoraRepository;
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

        return  new DonorDTO(donor.getUsername() , donor.getDateOfBirth(), donor.getGender(), donor.getBloodType().getType(), donor.getLocation().getLocationName(), donor.isVerified(),
                donor.getAppUser().getFirstName(), donor.getAppUser().getLastName(), donor.getAppUser().getPhoneNumber());
    }


    public DonationHistory createNewReservation(DonationHistoryDTO historyDTO) {
        Donor donor  =  donorRepository.findByUsername(historyDTO.getUsername());


        Optional<Appointment> opt2  =  appointmentRepository.findById(historyDTO.getAppointmentID());
        if(opt2.isEmpty()){
            return null;
        }
        Appointment appointment= opt2.get();


        /* Sav kod u komentarim sluzi za ispitivanje zadnjeg davanja krvi
        Set<DonationHistory> donationHistorySet = donor.getDonationHistory();
        Appointment lastAppointment = null;
        for(DonationHistory d: donationHistorySet){
            assert lastAppointment != null;
            if (d.getAppointment().getDateAndTime().isAfter(lastAppointment.getDateAndTime() )){
                lastAppointment = d.getAppointment();
            }
        }

        LocalDateTime appointmentDateTime = lastAppointment.getDateAndTime();
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
        if(appointmentDateTime.isAfter(threeMonthsAgo) ){
            //Prosla donacija je bila prije manje od 3 mjeseca, donor ne smije davati krv.
            return null;
        }
        else{
            DonationHistory history = new DonationHistory(donor, appointment, false);
            Long[] longPotvrda = historyDTO.getPotvrdeID();
            List<Potvrda> svePotvrde = potvrdaRepository.findAllById(Arrays.stream(longPotvrda).toList());

            for(Potvrda p: svePotvrde){
                PotvrdeDonora potvrdeDonora = new PotvrdeDonora(p,history,null,false);
                potvrdeDonoraRepository.save(potvrdeDonora);
            }


            historyRepository.save(history);
            return history;
        }*/
        DonationHistory history = new DonationHistory(donor, appointment, false);

        Long[] longPotvrda = historyDTO.getPotvrdeID();
        List<Potvrda> svePotvrde = new ArrayList<>();
        for(Long p: longPotvrda){
            svePotvrde.add(potvrdaRepository.findById(p).get());
        }

        for(Potvrda p: svePotvrde){
            PotvrdeDonora potvrdeDonora = new PotvrdeDonora(p,history,null,false);
            potvrdeDonoraRepository.save(potvrdeDonora);
        }
        historyRepository.save(history);

        return history;

    }


    /**
     * Method for getting list of all active donation reservations for user
     * @param username - username of user
     * @return list of active donation reservations
     */
    public List<DonationHistoryDTO> getDonationReservationByUsername(String username) {

        List<DonationHistoryDTO> donationHistoryDTOList = getAllDonationReservationByUsername(username);

        for (DonationHistoryDTO donationHistory : donationHistoryDTOList) {

            if(donationHistory.isFinished() )
                donationHistoryDTOList.remove(donationHistory);
        }


        return donationHistoryDTOList;
    }
    /**
     * Method for getting list of all  donation reservations for user (even finished ones)
     * @param username - username of user
     * @return list of active donation reservations
     */
    public List<DonationHistoryDTO> getAllDonationReservationByUsername(String username) {

        Optional<Donor> opt =  donorRepository.findDonorByUsername(username);
        //System.out.println("1111111111111111111111");
        if(opt.isEmpty()){
            return null;
        }
        Donor donor = opt.get();

        List<DonationHistory> donationHistoryList =historyRepository.findByDonor_id(donor.getDonor_id());
        //System.out.println("222222222222222");
        if(donationHistoryList.isEmpty()){
            return null;
        }


        List<DonationHistoryDTO> donationHistoryDTOList = new ArrayList<>();
        for (DonationHistory donationHistory : donationHistoryList) {
            Appointment appointment  =  appointmentRepository.findById(donationHistory.getAppointment().getAppointment_id()).orElse(null);
            if(appointment == null){
                throw new IllegalArgumentException("Appointment not found");
            }

            donationHistoryDTOList.add(new DonationHistoryDTO(appointment, donationHistory.isCame() , appointment.isFinished()));
        }


        return donationHistoryDTOList;
    }

    /**
     * Metoda traži sve aktivne akcije za doniranje krvi i vraća ih u listi
     * metoda selektira aktivne akcije preko datuma!
     * @param username - username of user (usless)
     * @return list of active donation dates
     */
    public List<Appointment> getListOfActiveDonationDates(String username) {

        //TODO prepraviti
        return appointmentRepository.findAll();
    }

    public List<Location> getListOfLocations(String username) {
        return locationRepository.findAll();
    }

    public List<BloodType> getListOfBloodTypes(String username) {
        return bloodTypeRepository.findAll();
    }

    /**
     * Method for getting list of all potvrde for user that are not expired and are given
     * @param usename - username of user
     * @return list of potvrde
     */
    public List<Potvrda> getListOfPotvrda(String usename) {
        List<PotvrdeDonora> lista = potvrdeDonoraRepository.findAll();
        return lista.stream().filter(
                potvrdeDonora -> potvrdeDonora.getDonationHistory().getDonor().getUsername().equals(usename))
        .filter(
                potvrdeDonora -> potvrdeDonora.getGiven() && potvrdeDonora.getExpiers().after(Date.from(Instant.from(LocalDateTime.now()))))
        .map(
                PotvrdeDonora::getPotvrda)
        .toList();
    }

    public List<Potvrda> getLAllPotvrde() {
        return potvrdaRepository.findAll();
    }
}
