package com.fer.progi.BloodDonation.security.services;

import com.fer.progi.BloodDonation.funcionality.models.Donor;
import com.fer.progi.BloodDonation.funcionality.repositorys.DonorRepository;
import com.fer.progi.BloodDonation.funcionality.models.AppUser;
import com.fer.progi.BloodDonation.funcionality.models.DTO.LoginResponseDTO;
import com.fer.progi.BloodDonation.funcionality.models.Role;
import com.fer.progi.BloodDonation.security.repository.AppUserRepository;
import com.fer.progi.BloodDonation.security.repository.RoleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    private AppUserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private TokenService tokenService;


    private DonorRepository donorRepository;



    public AuthenticationService(AppUserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService, DonorRepository donorRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.donorRepository = donorRepository;
    }

    public AppUser registerUser(String username, String password, String firstName, String lastName, String phoneNumber, Date dateOfBirth, String city, String address, String bloodType,String gender){

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("user").get();

        Set<Role> authorities = Set.of(userRole);

        var user = userRepository.save(new AppUser(username, firstName, lastName, phoneNumber, encodedPassword, authorities));
       Donor donor =  new Donor(username, dateOfBirth, gender, bloodType, city, address);
        donor.setAppUser(user);
        donorRepository.save(donor);
        return user;

    }

    public LoginResponseDTO loginUser(String username, String password){

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findAppUserByUsername(username).get(), token);

        }catch (AuthenticationException e){
            return new LoginResponseDTO(null, "");
        }

    }

}
