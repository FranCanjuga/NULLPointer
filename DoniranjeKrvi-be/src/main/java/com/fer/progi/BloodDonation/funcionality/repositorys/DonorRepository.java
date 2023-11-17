package com.fer.progi.BloodDonation.funcionality.repositorys;

import com.fer.progi.BloodDonation.funcionality.models.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonorRepository  extends JpaRepository<Donor, Long> {

    Optional<Donor> findDonorByUsername(String username);

    Donor findByUsername(String username);
}
