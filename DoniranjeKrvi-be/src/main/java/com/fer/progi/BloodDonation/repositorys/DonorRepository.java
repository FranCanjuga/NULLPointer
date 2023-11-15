package com.fer.progi.BloodDonation.repositorys;

import com.fer.progi.BloodDonation.models.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonorRepository  extends JpaRepository<Donor, Long> {

    Optional<Donor> findDonorByUsername(String username);

}
