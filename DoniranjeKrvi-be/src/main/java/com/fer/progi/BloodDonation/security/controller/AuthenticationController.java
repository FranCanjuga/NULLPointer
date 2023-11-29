package com.fer.progi.BloodDonation.security.controller;

import com.fer.progi.BloodDonation.funcionality.models.AppUser;
import com.fer.progi.BloodDonation.funcionality.models.DTO.LoginDTO;
import com.fer.progi.BloodDonation.funcionality.models.DTO.LoginResponseDTO;
import com.fer.progi.BloodDonation.funcionality.models.DTO.RegistrationDTO;
import com.fer.progi.BloodDonation.security.services.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    private AuthenticationService authenticationService;


    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public LoginResponseDTO LoginResponseDTO(@RequestBody RegistrationDTO body){
        AppUser user =  authenticationService.registerUser(body.getUsername(), body.getPassword(), body.getFirstName(),
                body.getLastName(), body.getPhoneNumber(), body.getDateOfBirth(), body.getCity(),
                  body.getAddress(), body.getBloodType() , body.getGender());

        return authenticationService.loginUser(body.getUsername(), body.getPassword());

    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody LoginDTO body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}
