package com.fer.progi.BloodDonation.funcionality.repositorys;

import com.fer.progi.BloodDonation.funcionality.models.Donor;
import com.fer.progi.BloodDonation.funcionality.models.Potvrda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PotvrdaRepository extends JpaRepository<Potvrda, Long> {

    Potvrda findPotvrdaByPotvrdaId(Long potvrdaId);


}
