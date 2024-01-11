package com.fer.progi.BloodDonation.funcionality.controllers;


import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonationHistoryDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.ApointmentDTO;
import com.fer.progi.BloodDonation.funcionality.models.Appointment;
import com.fer.progi.BloodDonation.funcionality.models.DonationHistory;
import com.fer.progi.BloodDonation.funcionality.models.Donor;
import com.fer.progi.BloodDonation.funcionality.services.DonorService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('CROSS')")
public class DonorController {
    @Autowired
    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }


    @GetMapping("/profile")
    public ResponseEntity<DonorDTO> getDonorData(@RequestBody String username) {


        DonorDTO donorData = donorService.getDonorDataByUsername(username);

        if (donorData != null) {
            return ResponseEntity.ok(donorData);
        } else {
              return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        }
    }


    //Stvaranje nove rezervacije
    @PostMapping("/create")
    public ResponseEntity<String> createDonationReservation(@RequestBody DonationHistoryDTO historyDTO) {

        /*gleda broj rezerviranih mjesta za
        Integer ReservationData = donorService.canReserve();*/

        //stvara novu instancu DonationHistory
        DonationHistory history = donorService.createNewReservation(historyDTO);
        if(history != null){
            return ResponseEntity.ok("Donation reservation created successfully.");
        }
        else{
            return ResponseEntity.badRequest().body("ERROR: Unable to create Donation reservation");
        }


    }


    //Get metoda za dohvacanje aktivnih rezervacija preko username-a
    @GetMapping("/ActiveReservations/{username}")
    public ResponseEntity<DonationHistoryDTO> getDonationReservationByUsername(@PathVariable String username) {

        DonationHistoryDTO historyData = donorService.getDonationReservationByUsername(username);

        if (historyData != null) {
            return ResponseEntity.ok(historyData);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

    }

    @GetMapping("/AllActiveDates")
    public ResponseEntity<List<Appointment>> getAllActiveDates(@PathVariable ApointmentDTO apointmentDTO) {
        List<Appointment> activeDates = donorService.getListOfActiveDonationDates(apointmentDTO);

        if (activeDates != null && !activeDates.isEmpty()) {
            return ResponseEntity.ok(activeDates);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }


}
