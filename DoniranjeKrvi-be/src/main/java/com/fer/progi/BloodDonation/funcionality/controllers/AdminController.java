package com.fer.progi.BloodDonation.funcionality.controllers;

import com.fer.progi.BloodDonation.funcionality.models.Donor;
import com.fer.progi.BloodDonation.funcionality.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

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

    @PostMapping("/approveDonor")
    public ResponseEntity<Object> approveDonor(@RequestBody String username) {
        boolean bool = adminService.approveDonor(username);

        if (bool) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(null);
    }

    @PostMapping("/rejectDonor")
    public ResponseEntity<Object> rejectDonor(@RequestBody String username) {
        boolean bool = adminService.rejectDonor(username);

        if (bool) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(null);
    }

}