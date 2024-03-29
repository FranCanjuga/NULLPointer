package com.fer.progi.BloodDonation.funcionality.repositorys;

import com.fer.progi.BloodDonation.funcionality.models.Appointment;
import com.fer.progi.BloodDonation.funcionality.models.DonationHistory;
import com.fer.progi.BloodDonation.funcionality.models.PotvrdeDonora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Override
    List<Appointment> findAll();

    //Optional<Appointment> findAppointmentByAppointmentID(Long appointmentID);

}
