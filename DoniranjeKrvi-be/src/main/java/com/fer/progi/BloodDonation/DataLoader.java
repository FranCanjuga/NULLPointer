package com.fer.progi.BloodDonation;

import com.fer.progi.BloodDonation.funcionality.models.Role;
import com.fer.progi.BloodDonation.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        addRolesIfNotExist("admin");
        addRolesIfNotExist("institution");
        addRolesIfNotExist("cross");
        addRolesIfNotExist("user");
    }

    private void addRolesIfNotExist(String roleName) {
        if (!roleRepository.existsByAuthority(roleName)) {
            Role role = new Role(roleName);
            roleRepository.save(role);
        }
    }
}