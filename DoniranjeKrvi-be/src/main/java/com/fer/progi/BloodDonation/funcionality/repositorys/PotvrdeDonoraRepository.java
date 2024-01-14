package com.fer.progi.BloodDonation.funcionality.repositorys;

import com.fer.progi.BloodDonation.funcionality.models.DonationHistory;
import com.fer.progi.BloodDonation.funcionality.models.Potvrda;
import com.fer.progi.BloodDonation.funcionality.models.PotvrdeDonora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PotvrdeDonoraRepository extends JpaRepository<PotvrdeDonora, Long> {

    default List<PotvrdeDonora> findByDontionHistoryId(Long donationHistoryId){
        return this.findAll()
                .stream()
                .filter(potvrdeDonora -> potvrdeDonora.getDonationHistory().getDonationHistory_id().equals(donationHistoryId))
                .toList();
    }
}
