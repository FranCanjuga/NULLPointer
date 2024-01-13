package com.fer.progi.BloodDonation.funcionality.repositorys;

import com.fer.progi.BloodDonation.funcionality.models.Donor;
import com.fer.progi.BloodDonation.funcionality.models.Potvrda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PotvrdaRepository extends JpaRepository<Potvrda, Long> {

    //Potvrda findPotvrdaByPotvrda_id(Long Potvrda_id);

    @Override
    List<Potvrda> findAll();

}
