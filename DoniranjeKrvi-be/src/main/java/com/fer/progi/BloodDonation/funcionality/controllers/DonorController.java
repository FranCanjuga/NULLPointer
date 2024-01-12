package com.fer.progi.BloodDonation.funcionality.controllers;


import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonationHistoryDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.ApointmentDTO;
import com.fer.progi.BloodDonation.funcionality.models.*;
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
@PreAuthorize("hasRole('user')")
public class DonorController {
    @Autowired
    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }


    @GetMapping("/profile/{username}")
    public ResponseEntity<DonorDTO> getDonorData(@PathVariable String username) {

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

    @GetMapping("/AllActiveDates/{username}")
    public ResponseEntity<List<Appointment>> getAllActiveDates(@PathVariable String username) {
        List<Appointment> activeDates = donorService.getListOfActiveDonationDates(username);

        if (activeDates != null && !activeDates.isEmpty()) {
            return ResponseEntity.ok(activeDates);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @GetMapping("/getLocations/{username}")
    public ResponseEntity<List<Location>> getAllLocations(@PathVariable String username) {
        List<Location> locationList = donorService.getListOfLocations(username);

        if (locationList != null && !locationList.isEmpty()) {
            return ResponseEntity.ok(locationList);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @GetMapping("/getBloodTypes/{username}")
    public ResponseEntity<List<BloodType>> getAllBloodTypes(@PathVariable String username) {
        List<BloodType> bloodTypeListList = donorService.getListOfBloodTypes(username);

        if (bloodTypeListList != null && !bloodTypeListList.isEmpty()) {
            return ResponseEntity.ok(bloodTypeListList);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @GetMapping("/getPotvrdeDonora/{username}")
    public ResponseEntity<List<Potvrda>> getPotvrde(@PathVariable String username) {
        List<Potvrda> listPotvrda = donorService.getListOfPotvrda(username);

        if (listPotvrda != null && !listPotvrda.isEmpty()) {
            return ResponseEntity.ok(listPotvrda);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }


    /**
     * Method for getting list of all potvrde that donor can chose wen creating new reservation
     * @return list of potvrde
     */
    @GetMapping("/getPotvrde")
    public ResponseEntity<List<Potvrda>> getPotvrde() {
        List<Potvrda> listPotvrda = donorService.getLAllPotvrde();

        if (listPotvrda != null && !listPotvrda.isEmpty()) {
            return ResponseEntity.ok(listPotvrda);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

}
