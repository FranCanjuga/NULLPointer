package com.fer.progi.BloodDonation.funcionality.repositorys;


import com.fer.progi.BloodDonation.funcionality.models.Appointment;
import com.fer.progi.BloodDonation.funcionality.models.DonationHistory;
import com.fer.progi.BloodDonation.funcionality.models.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrossRepository extends JpaRepository<Appointment, Long>{
}
