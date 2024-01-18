package com.fer.progi.BloodDonation.funcionality.repositorys;

import com.fer.progi.BloodDonation.funcionality.models.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DonorRepository  extends JpaRepository<Donor, Long> {

    Optional<Donor> findDonorByUsername(String username);

    Donor findByUsername(String username);
}
