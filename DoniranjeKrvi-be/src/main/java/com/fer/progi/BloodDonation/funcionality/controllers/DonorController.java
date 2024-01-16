package com.fer.progi.BloodDonation.funcionality.controllers;


import com.fer.progi.BloodDonation.funcionality.controllers.dto.*;
import com.fer.progi.BloodDonation.funcionality.models.*;
import com.fer.progi.BloodDonation.funcionality.services.CrossService;
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

    @Autowired
    private final CrossService crossService;

    public DonorController(DonorService donorService, CrossService crossService) {
        this.donorService = donorService;
        this.crossService = crossService;
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
    public ResponseEntity<List<DonationHistoryDTO>> getActiveDonationReservationByUsername(@PathVariable String username) {

        List<DonationHistoryDTO> historyData = donorService.getDonationReservationByUsername(username);

        if (historyData != null) {
            return ResponseEntity.ok(historyData);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

    }

    @DeleteMapping("/deleteReservation")
    public ResponseEntity<String> deleteReservation(@PathVariable DeleteAppointmentDTO deleteAppointmentDTO) {
        boolean deleteStatus = donorService.deleteReservationById(deleteAppointmentDTO);

        if (deleteStatus) {
            return ResponseEntity.ok("Reservation deleted successfully");
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("ERROR: Unable to delete Donation reservation");
        }
    }

    /**
     * Method for getting list of all  appointments for donor (even finished ones)
     * @param username username of donor
     * @return list of all active appointments for donor
     */
    @GetMapping("/AllDonorReservations/{username}")
    public ResponseEntity<List<DonationHistoryDTO>> getAllDonationReservationByUsername(@PathVariable String username) {

        try {
            List<DonationHistoryDTO> historyData = donorService.getAllDonationReservationByUsername(username);
            return ResponseEntity.ok(historyData);
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

    }
    /**
     * Method for getting list of all  active appointments donor can sing in for (even finished ones)
     * @param username username of donor
     * @return list of all active appointments for donor
     */
    @GetMapping("/AllActiveDates/{username}")
    public ResponseEntity<List<AppointmentGetDTO>> getAllActiveDates(@PathVariable String username) {
        List<AppointmentGetDTO> activeDates = donorService.getListOfActiveDonationDates(username);

        if (activeDates != null && !activeDates.isEmpty()) {
            return ResponseEntity.ok(activeDates);
        }
        else {
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

    @GetMapping("/ActiveAppointments")
    public AppointmentsResponseDTO[] getActiveAppointments() {
        AppointmentsResponseDTO[] data;

        try{
            data= crossService.getActiveAppointments();
        }catch (Exception e) {
            return null;
        }

        return data;
    }
}
