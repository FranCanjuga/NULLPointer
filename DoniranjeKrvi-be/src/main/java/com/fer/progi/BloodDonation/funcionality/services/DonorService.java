package com.fer.progi.BloodDonation.funcionality.services;

import com.fer.progi.BloodDonation.funcionality.controllers.dto.*;
import com.fer.progi.BloodDonation.funcionality.models.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.fer.progi.BloodDonation.funcionality.repositorys.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

    private final PriznanjaDonoraRepository priznanjaDonoraRepository;

    @Autowired
    public DonorService(DonorRepository donorRepository, DonationHistoryRepository historyRepository, AppointmentRepository appointmentRepository, LocationRepository locationRepository, BloodTypeRepository bloodTypeRepository, PotvrdaRepository potvrdaRepository, PotvrdeDonoraRepository potvrdeDonoraRepository, PriznanjaDonoraRepository priznanjaDonoraRepository) {

        this.donorRepository = donorRepository;
        this.historyRepository = historyRepository;
        this.appointmentRepository = appointmentRepository;
        this.locationRepository = locationRepository;
        this.bloodTypeRepository = bloodTypeRepository;
        this.potvrdaRepository = potvrdaRepository;
        this.potvrdeDonoraRepository = potvrdeDonoraRepository;
        this.priznanjaDonoraRepository = priznanjaDonoraRepository;
    }


    public DonorDTO getDonorDataByUsername(String username) {
        Optional<Donor> opt =  donorRepository.findDonorByUsername(username);



        if(opt.isEmpty()){
            return null;
        }
        Donor donor = opt.get();


        List<PriznanjaDonora> priznanjaDonora = priznanjaDonoraRepository.findAll();

        Priznanje priznanje = priznanjaDonora.stream().filter(
                priznanjaDonora1 -> priznanjaDonora1.getDonationHistory().getDonor().getUsername().equals(username))
                .sorted( (p1 , p2) ->{
                    return p1.getPriznanje().getCondition() - p2.getPriznanje().getCondition();
                } )
                .map(PriznanjaDonora::getPriznanje)
                .findFirst()
                .orElse(null);

        return  new DonorDTO(donor.getUsername() , donor.getDateOfBirth(), donor.getGender(), donor.getBloodType().getType(), donor.getLocation().getLocationName(), donor.isVerified(),
                donor.getAppUser().getFirstName(), donor.getAppUser().getLastName(), donor.getAppUser().getPhoneNumber() , priznanje == null ? null : priznanje.getNamePriznanje());
    }

    public DonationHistory createNewReservation(ReservationDTO historyDTO) {
        Donor donor  =  donorRepository.findByUsername(historyDTO.getUsername());

        if(!donor.isVerified()) {
            throw new IllegalArgumentException("Donor nije verificiran.Molimo pričekajte da vas administrator verificira.");
        }

        Optional<Appointment> opt2  =  appointmentRepository.findById(historyDTO.getAppointmentID());
        if(opt2.isEmpty()){
            return null;
        }
        Appointment appointment= opt2.get();

        try {


            DonationHistory history = new DonationHistory(donor, appointment, false);

            Long[] longPotvrda = historyDTO.getPotvrdeID();
            List<Potvrda> svePotvrde = new ArrayList<>();
            for (Long p : longPotvrda) {
                svePotvrde.add(potvrdaRepository.findById(p).get());
            }

            for (Potvrda p : svePotvrde) {
                PotvrdeDonora potvrdeDonora = new PotvrdeDonora(p, history, null, false);
                potvrdeDonoraRepository.save(potvrdeDonora);
            }
            historyRepository.save(history);

            return history;
        }catch (Exception e){
            throw new IllegalArgumentException("već ste rezervirali termin za ovu akciju");
        }

    }

    /**
     * Method for getting list of all active donation reservations for user
     * @param username - username of user
     * @return list of active donation reservations
     */
    public List<DonationHistoryDTO> getDonationReservationByUsername(String username) {

        List<DonationHistoryDTO> donationHistoryDTOList = getAllDonationReservationByUsername(username);
        donationHistoryDTOList.removeIf(DonationHistoryDTO::isFinished);



        return donationHistoryDTOList;
    }

    public boolean deleteReservationById(DeleteAppointmentDTO deleteAppointmentDTO) {
        try {
            Optional<Donor> opt = donorRepository.findDonorByUsername(deleteAppointmentDTO.getUsername());
            Donor donor = opt.get();

            Optional<Appointment> opt2 = appointmentRepository.findById(deleteAppointmentDTO.getAppointmentID());
            Appointment appointment = opt2.get();


            //find DonatoionHistoryId -> PotvrdeDonoraRepo
            DonationHistory history = historyRepository.findDonationHistoriesByAppointmentAndDonor(appointment, donor);

            List<PotvrdeDonora> potvrdeDonora = potvrdeDonoraRepository.findByDontionHistoryId(history.getDonationHistory_id());

            potvrdeDonoraRepository.deleteAll(potvrdeDonora);


            //brise donation history za specificni appointment
            historyRepository.delete(history);

            return true;
        }catch (Exception e){
            return false;
        }
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

            donationHistoryDTOList.add(new DonationHistoryDTO(donationHistory ,appointment, donationHistory.isCame() , appointment.isFinished()));
        }

      //  donationHistoryDTOList.removeIf(donationHistoryDTO -> !donationHistoryDTO.isFinished() || !donationHistoryDTO.isCame());


        return donationHistoryDTOList;
    }

    /**
     * Metoda traži sve aktivne akcije za doniranje krvi i vraća ih u listi
     * metoda selektira aktivne akcije preko datuma!
     * @param username - username of user (usless)
     * @return list of active donation dates
     */
    public List<AppointmentGetDTO> getListOfActiveDonationDates(String username) {

        List<Appointment> allAppointments = appointmentRepository.findAll();
        if(allAppointments.isEmpty()){return null;}
        List<AppointmentGetDTO> activeAppointments = new ArrayList<>();

        Optional<Donor> opt  =  donorRepository.findDonorByUsername(username);
        Donor donor= opt.get();

        Set<DonationHistory> donorsAppointments = donor.getDonationHistory();
        donorsAppointments =  donorsAppointments.stream().filter(donationHistory -> !donationHistory.getAppointment().isFinished()).collect(Collectors.toSet());

        for(DonationHistory d:donorsAppointments){
            Appointment a = d.getAppointment();
            allAppointments.removeIf(dto -> Objects.equals(dto.getAppointment_id(), a.getAppointment_id()));
        }

        for(Appointment a: allAppointments){
            if(a.getDateAndTime().isAfter(LocalDateTime.now())){
                //activeAppointments.add(a);
                AppointmentGetDTO appDTO = new AppointmentGetDTO(a.getAppointment_id(),a.getLocation(),a.getDateAndTime(),a.isCriticalAction());
                activeAppointments.add(appDTO);
            }
        }

        if(activeAppointments.isEmpty()){
            return null;
        }
        else{
            return activeAppointments;
        }

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
    public List<PotvrdeDto> getListOfPotvrda(String usename) {
        List<PotvrdeDonora> lista = potvrdeDonoraRepository.findAll();
        var var1 = new ArrayList<>(lista.stream().filter(
                        potvrdeDonora -> potvrdeDonora.getDonationHistory().getDonor().getUsername().equals(usename))
                .toList());

        List<Potvrda> potvrdaList = new ArrayList<>();
        for(var potvrda : var1){
            if(potvrda.getGiven() && potvrda.getExpiers().after(Date.from(Instant.now()))){
            potvrdaList.add(potvrda.getPotvrda());
            }
        }
        return potvrdaList.stream().map(PotvrdeDto::new).toList();
    }

    public List<PotvrdeDto> getLAllPotvrde() {
        return potvrdaRepository.findAll().stream().map(PotvrdeDto::new).toList();
    }


}
