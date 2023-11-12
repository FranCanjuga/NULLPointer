package com.fer.progi.BloodDonation.security.repository;

import com.fer.progi.BloodDonation.security.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findAppUserByUsername(String username);
}
