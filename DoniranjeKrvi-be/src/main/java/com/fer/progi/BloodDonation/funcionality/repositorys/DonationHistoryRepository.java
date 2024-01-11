package com.fer.progi.BloodDonation.funcionality.repositorys;

import com.fer.progi.BloodDonation.funcionality.models.DonationHistory;
import com.fer.progi.BloodDonation.funcionality.models.Donor;
import com.fer.progi.BloodDonation.funcionality.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Long> {

    Optional<DonationHistory> findDonationHistoryByUsername(String username);
    //Optional<DonationHistory> findDonationHistoryById(Long id);

    DonationHistory findByUsername(String username);
    //DonationHistory findById(Long id);
    @Override
    List<DonationHistory> findAll();
}
