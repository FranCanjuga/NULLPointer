package com.fer.progi.BloodDonation.funcionality.repositorys;

import com.fer.progi.BloodDonation.funcionality.models.Potvrda;
import com.fer.progi.BloodDonation.funcionality.models.PotvrdeDonora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PotvrdeDonoraRepository extends JpaRepository<PotvrdeDonora, Long> {
}
