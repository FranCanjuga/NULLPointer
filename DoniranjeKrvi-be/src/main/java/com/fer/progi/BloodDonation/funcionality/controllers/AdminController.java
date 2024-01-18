package com.fer.progi.BloodDonation.funcionality.controllers;

import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.models.Donor;
import com.fer.progi.BloodDonation.funcionality.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('admin')")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    //Dohvaca sve donore
    @GetMapping("/allDonors")
    public List<DonorDTO> listAll() {
        return adminService.getAllDonors();
    }

    //Dohvaca sve donore kojima je jos potrebna potvrda registracije
    @GetMapping("/unregistered")
    public List<DonorDTO> listUnregistered() {
        return adminService.getUnregisteredDonors();
    }

    /**
     * Approves donor registration.Donor will be marked as verified.
     * @param username username of donor that passed vertification process
     * @return response entity with status 200 if donor was successfully approved, 400 otherwise
     */
    @PostMapping("/approveDonor/{username}")
    public ResponseEntity<Object> approveDonor(@PathVariable String username) {
        boolean bool = adminService.approveDonor(username);

        if (bool) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(null);
    }

    /**
     * Rejects donor registration.Donor will be deleted from database.
     * @param username username of donor that failed in vertification process
     * @return response entity with status 200 if donor was successfully deleted, 400 otherwise
     */
    @PostMapping("/rejectDonor/{username}")
    public ResponseEntity<Object> rejectDonor(@PathVariable String username) {
        boolean bool = adminService.rejectDonor(username);

        if (bool) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(null);
    }

}
