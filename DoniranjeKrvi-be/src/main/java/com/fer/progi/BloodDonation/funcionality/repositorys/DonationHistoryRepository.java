package com.fer.progi.BloodDonation.funcionality.repositorys;

import com.fer.progi.BloodDonation.funcionality.models.DonationHistory;
import com.fer.progi.BloodDonation.funcionality.models.Donor;
import com.fer.progi.BloodDonation.funcionality.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Long> {

    Optional<DonationHistory> findDonationHistoryByDonorUsername(String username);
    //Optional<DonationHistory> findDonationHistoryById(Long id);

    DonationHistory findByDonorUsername(String username);
    //DonationHistory findById(Long id);
}
