package com.fer.progi.BloodDonation.funcionality.repositorys;



import com.fer.progi.BloodDonation.funcionality.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface AkcijaKrvRepository extends JpaRepository<AkcijaKrv, Long>{
}
