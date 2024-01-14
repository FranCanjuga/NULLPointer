package com.fer.progi.BloodDonation.funcionality.services;

import com.fer.progi.BloodDonation.funcionality.controllers.dto.ApointmentDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.AppointmentsResponseDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.models.*;
import com.fer.progi.BloodDonation.funcionality.repositorys.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CrossService {

    @Autowired
    private CrossRepository crossRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private BloodTypeRepository bloodTypeRepository;

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    @Autowired
    private AkcijaKrvRepository akcijaKrvRepository;


    /**
     * Creates new appointment.
     * @param appointment appointment in ApointmentDTO format
     */
    public void addAppointment(ApointmentDTO appointment) {

        Location location = locationRepository.findById(appointment.locationID).orElse(null);
        if(location == null) {
            throw new IllegalArgumentException("Location with given ID does not exist");
        }

        LocalDateTime dateTime;
        try{
             dateTime = LocalDateTime.parse(appointment.getDateAndTime());
        } catch (Exception e) {
            throw new IllegalArgumentException("Date and time format is not valid");
        }

//TOO DOO - provjeriti da li je datum u buducnosti


//        -provjeri da nema konflikta u datumima na toj lokaciji

        Appointment appointmentModel = new Appointment();
        appointmentModel.setLocation(location);
        appointmentModel.setCriticalAction(appointment.isCriticalAction());
        appointmentModel.setDateAndTime(dateTime);
        crossRepository.save(appointmentModel);



        //Connect appointment with bloodTypes if appointment is critical
        if(appointment.isCriticalAction()){

            List<BloodType> bloodTypes =bloodTypeRepository.findByTypes(appointment.getBloodTypes());

            for (BloodType bloodType : bloodTypes) {
                AkcijaKrv akcijaKrv = new AkcijaKrv();
                akcijaKrv.setAppointment(appointmentModel);
                akcijaKrv.setBloodType(bloodType);
                akcijaKrvRepository.save(akcijaKrv);
            }

        }

    }


    /**
     * Returns all registered donors for given appointment.
     * @param appointmentId id of appointment in body
     * @return array of donors in DonorDTO format
     */
    public DonorDTO[] getRegisteredForAppointment(Long appointmentId) {
       var appointments = crossRepository.findById(appointmentId).
                  orElseThrow(() -> new IllegalArgumentException("Appointment with given ID does not exist"));
       var donors = donationHistoryRepository.findAll()
               .stream()
               .filter(donationHistory -> Objects.equals(donationHistory.getAppointment().getAppointment_id(), appointmentId))
               .map(donationHistory ->
                       new DonorDTO(donationHistory.getDonor())).toList();

       return donors.toArray(DonorDTO[]::new);

    }


    /**
     * Finishes appointment with given ID and marks donors with given usernames as came.
     * @param appointmentId id of appointment
     * @param usernames array of usernames of donors that came
     */
    public void finishAppointment(Long appointmentId, String[] usernames) {

            var donationHistories = donationHistoryRepository.findAll()
                    .stream()
                    .filter(donationHistory -> Objects.equals(donationHistory.getAppointment().getAppointment_id(), appointmentId)).toList();

            for (String username : usernames) {
                var donationHistory = donationHistories.stream()
                        .filter(donationHistory1 -> donationHistory1.getDonor().getUsername().equals(username))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Donor with given username is not registered for this appointment"));

//TO DOO - dodati davanje priznanja i potvrda donoru
                int numberOfDonations = countDonorDonations(username);

                switch (numberOfDonations){
                    case 10 -> {
                        //dodaj  Broncano priznanje
                    }
                    case 20 -> {
                        //dodaj priznanje Srebrno priznanje
                    }
                    case 30 -> {
                        //dodaj priznanje Zlatno priznanje
                    }
                }

//TO DOO - dodati potvrdu donoru

                donationHistory.setCame(true);
                donationHistoryRepository.save(donationHistory);
            }

    }

    private int countDonorDonations(String username) {
        return donationHistoryRepository.findAll()
                .stream()
                .filter(donationHistory -> donationHistory.getDonor().getUsername().equals(username))
                .filter(DonationHistory::isCame).toList()
                .size();
    }

    /**
     * Returns all active appointments.
     * @return array active appointments
     */
    public AppointmentsResponseDTO[] getActiveAppointments() {

        return crossRepository.findAll()
                .stream()
                .filter(appointment -> !appointment.isFinished())
                .map(AppointmentsResponseDTO::new).toList()
                .toArray(AppointmentsResponseDTO[]::new);
    }

    /**
     * Deletes appointment with given ID.
     * @param appointmentID id of appointment
     */
    public void deleteAppointment(Long appointmentID) {

        Appointment appointment = crossRepository.findById(appointmentID).orElse(null);

        if(appointment == null) {
            throw new IllegalArgumentException("Appointment with given ID does not exist");
        }
        if(appointment.isFinished()) {
            throw new IllegalArgumentException("Appointment is already finished");
        }

        crossRepository.delete(appointment);

    }
}
