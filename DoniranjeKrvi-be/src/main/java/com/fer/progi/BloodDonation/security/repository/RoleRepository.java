package com.fer.progi.BloodDonation.security.repository;

import com.fer.progi.BloodDonation.funcionality.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByAuthority(String authority);

    boolean existsByAuthority(String roleName);
}
