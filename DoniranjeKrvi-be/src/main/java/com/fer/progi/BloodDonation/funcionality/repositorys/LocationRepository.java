package com.fer.progi.BloodDonation.funcionality.repositorys;

import com.fer.progi.BloodDonation.funcionality.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {
    Location findLocationByLocationName(String name);
}
