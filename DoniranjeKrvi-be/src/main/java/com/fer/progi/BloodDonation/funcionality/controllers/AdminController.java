package com.fer.progi.BloodDonation.funcionality.controllers;

import com.fer.progi.BloodDonation.funcionality.models.AppUser;
import com.fer.progi.BloodDonation.funcionality.models.Donor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private final AdminService adminService;

    //Dohvaca sve donore
    @GetMapping("/allDonors")
    public List<Donor> listAll() {
        return adminService.getAllDonors();
    }

    //Dohvaca sve donore kojima je jos potrebna potvrda registracije
    @GetMapping("/unregistered")
    public List<Donor> listUnregistered() {
        return adminService.getUnregisteredDonors();
    }

}
