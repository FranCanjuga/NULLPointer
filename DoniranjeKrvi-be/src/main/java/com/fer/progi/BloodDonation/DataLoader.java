package com.fer.progi.BloodDonation;

import com.fer.progi.BloodDonation.funcionality.models.AppUser;
import com.fer.progi.BloodDonation.funcionality.models.Role;
import com.fer.progi.BloodDonation.security.repository.AppUserRepository;
import com.fer.progi.BloodDonation.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AppUserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(RoleRepository roleRepository, AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        addRolesIfNotExist("admin");
        addRolesIfNotExist("institution");
        addRolesIfNotExist("cross");
        addRolesIfNotExist("user");

        Role r= roleRepository.getReferenceById(1);



        String encodedPassword = passwordEncoder.encode("admin");
        Role userRole = roleRepository.findByAuthority("admin").get();
        Set<Role> authorities = Set.of(userRole);

        userRepository.save(new AppUser("admin@gmail.com", "admin", null, null, encodedPassword, authorities));


         encodedPassword = passwordEncoder.encode("cross");
         userRole = roleRepository.findByAuthority("cross").get();
         authorities = Set.of(userRole);

        userRepository.save(new AppUser("cross@gmail.com", "cross", null, null, encodedPassword, authorities));

        encodedPassword = passwordEncoder.encode("institution");
        userRole = roleRepository.findByAuthority("institution").get();
        authorities = Set.of(userRole);

        userRepository.save(new AppUser("institution@gmail.com", "institution", null, null, encodedPassword, authorities));



    }

    private void addRolesIfNotExist(String roleName) {
        if (!roleRepository.existsByAuthority(roleName)) {
            Role role = new Role(roleName);
            roleRepository.save(role);
        }
    }
}