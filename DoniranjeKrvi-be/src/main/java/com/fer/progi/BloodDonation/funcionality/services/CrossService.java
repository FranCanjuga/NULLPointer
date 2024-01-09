package com.fer.progi.BloodDonation.funcionality.services;

import com.fer.progi.BloodDonation.funcionality.controllers.dto.ApointmentDTO;
import com.fer.progi.BloodDonation.funcionality.models.AkcijaKrv;
import com.fer.progi.BloodDonation.funcionality.models.Appointment;
import com.fer.progi.BloodDonation.funcionality.models.BloodType;
import com.fer.progi.BloodDonation.funcionality.models.Location;
import com.fer.progi.BloodDonation.funcionality.repositorys.AkcijaKrvRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.BloodTypeRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.CrossRepository;
import com.fer.progi.BloodDonation.funcionality.repositorys.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CrossService {

    @Autowired
    private CrossRepository crossRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private BloodTypeRepository bloodTypeRepository;

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
}
