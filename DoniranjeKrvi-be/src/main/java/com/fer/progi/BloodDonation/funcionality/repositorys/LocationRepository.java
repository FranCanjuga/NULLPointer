package com.fer.progi.BloodDonation.funcionality.repositorys;


import com.fer.progi.BloodDonation.funcionality.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findLocationByLocationName(String name);

    @Override
    List<Location> findAll();

}
