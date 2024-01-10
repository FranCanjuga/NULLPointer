package com.fer.progi.BloodDonation.funcionality.controllers;


import com.fer.progi.BloodDonation.funcionality.controllers.dto.ApointmentDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.AppointmentFinishedDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.models.Location;
import com.fer.progi.BloodDonation.funcionality.services.CrossService;
import com.fer.progi.BloodDonation.funcionality.services.DonorService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/cross")
@PreAuthorize("hasRole('CROSS')")
public class CrossControler {

    @Autowired
    private final CrossService crossService;

    public CrossControler(CrossService crossService) {
        this.crossService = crossService;
    }

    @GetMapping("/RegisteredForAppointment")
    public DonorDTO[] getRegisteredForAppointment(@RequestBody Long appointmentId ) {
       DonorDTO[] data;

       try{
           data= crossService.getRegisteredForAppointment(appointmentId);
       }catch (Exception e) {
        return null;
       }

        return data;
    }

    @GetMapping("/addAppointment")
    public ResponseEntity<Object> addAppointment(@RequestBody ApointmentDTO apointment) {

     try{
            crossService.addAppointment(apointment);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
        }

    }




    @GetMapping("/AppointmentFinished")
    public ResponseEntity<Object> AppointmentFinished(@RequestBody AppointmentFinishedDTO request) {

        try{
            crossService.finishAppointment( request.getAppointmentID() , request.getUsernames() ) ;
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
        }


    }


}
