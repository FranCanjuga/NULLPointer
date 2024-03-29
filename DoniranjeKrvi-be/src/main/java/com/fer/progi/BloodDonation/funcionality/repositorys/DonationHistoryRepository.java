package com.fer.progi.BloodDonation.funcionality.repositorys;

import com.fer.progi.BloodDonation.funcionality.models.Appointment;
import com.fer.progi.BloodDonation.funcionality.models.DonationHistory;
import com.fer.progi.BloodDonation.funcionality.models.Donor;
import com.fer.progi.BloodDonation.funcionality.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Long> {

    Optional<DonationHistory> findDonationHistoryByDonorUsername(String username);
    //Optional<DonationHistory> findDonationHistoryById(Long id);

    DonationHistory findByDonorUsername(String username);
    @Override
    List<DonationHistory> findAll();

    DonationHistory findDonationHistoriesByAppointmentAndDonor(Appointment appointment, Donor donor);

    default List<DonationHistory> findByDonor_id(Long donor_id){
       return this.findAll()
               .stream()
               .filter(donationHistory -> donationHistory.getDonor().getDonor_id().equals(donor_id))
               .toList();
    }
}
