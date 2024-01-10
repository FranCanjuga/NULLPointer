package com.fer.progi.BloodDonation.funcionality.services;

import com.fer.progi.BloodDonation.funcionality.controllers.dto.ApointmentDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.models.*;
import com.fer.progi.BloodDonation.funcionality.repositorys.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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

    public void addAppointment(ApointmentDTO appointment) {

        Location location = locationRepository.findById(appointment.getLocationID()).orElse(null);
        if(location == null) {
            throw new IllegalArgumentException("Location with given ID does not exist");
        }

        LocalDateTime dateTime;
        try{
             dateTime = LocalDateTime.parse(appointment.getDateAndTime());
        } catch (Exception e) {
            throw new IllegalArgumentException("Date and time format is not valid");
        }

        Appointment appointmentModel = new Appointment();
        appointmentModel.setLocation(location);
        appointmentModel.setCriticalAction(appointment.isCriticalAction());
        appointmentModel.setDateAndTime(dateTime);


        //Connect appointment with bloodTypes if appointment is critical
        if(appointment.isCriticalAction()){

            List<BloodType> bloodTypes =bloodTypeRepository.findByType(appointment.getBloodTypes());

            for (BloodType bloodType : bloodTypes) {
                AkcijaKrv akcijaKrv = new AkcijaKrv();
                akcijaKrv.setAppointment(appointmentModel);
                akcijaKrv.setBloodType(bloodType);
                akcijaKrvRepository.save(akcijaKrv);
            }

        }
    }

    public DonorDTO[] getRegisteredForAppointment(Long appointmentId) {
       var appointments = crossRepository.findById(appointmentId).
                  orElseThrow(() -> new IllegalArgumentException("Appointment with given ID does not exist"));
       var donors = donationHistoryRepository.findAll()
               .stream()
               .filter(donationHistory -> Objects.equals(donationHistory.getAppointment().getAppointmentID(), appointmentId))
               .map(donationHistory ->
                       new DonorDTO(donationHistory.getDonor())).toList();

       return donors.toArray(DonorDTO[]::new);

    }

    public void finishAppointment(Long appointmentId, String[] usernames) {

            var donationHistories = donationHistoryRepository.findAll()
                    .stream()
                    .filter(donationHistory -> Objects.equals(donationHistory.getAppointment().getAppointmentID(), appointmentId)).toList();

            for (String username : usernames) {
                var donationHistory = donationHistories.stream()
                        .filter(donationHistory1 -> donationHistory1.getDonor().getUsername().equals(username))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Donor with given username is not registered for this appointment"));
    //TO DOO - dodati davanje priznanja i potvrda donoru

                donationHistory.setCame(true);
                donationHistoryRepository.save(donationHistory);
            }

    }
}
