package com.fer.progi.BloodDonation.funcionality.controllers;


import com.fer.progi.BloodDonation.funcionality.controllers.dto.ApointmentDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.AppointmentFinishedDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.AppointmentsResponseDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.models.Appointment;
import com.fer.progi.BloodDonation.funcionality.models.Location;
import com.fer.progi.BloodDonation.funcionality.services.CrossService;
import com.fer.progi.BloodDonation.funcionality.services.DonorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/cross")
@PreAuthorize("hasRole('cross')")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CrossControler {

    private final CrossService crossService;

    private final DonorService donorService;


    /**
     * Returns all registered donors for given appointment.
     * @param appointmentId id of appointment in body
     * @return array of donors in DonorDTO format
     */
    @GetMapping("/RegisteredForAppointment/{appointmentId}")
    public DonorDTO[] getRegisteredForAppointment(@PathVariable Long appointmentId ) {
       DonorDTO[] data;

       try{
           data= crossService.getRegisteredForAppointment(appointmentId);
       }catch (Exception e) {
        return null;
       }

        return data;
    }


    /**
     * Returns all active appointments.
     * @return array active appointments
     */
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


    /**
     * Methos for adding  appointment.
     * @param apointment appointment data in ApointmentDTO format in body
     * @return response
     */
    @PostMapping("/addAppointment")
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




    /**
     * Demands appointment id and list of usernames of donors that came to the appointment.
     * Used to finish appointment and update donor's donation history and give
     * donors that came nesesary potvrde and awards .
     * @return response
     */
    @PostMapping("/AppointmentFinished")
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

    /**
     * Demands appointment id .
     * Used to delete appointment
     * @return response
     */
    @PostMapping("/AppointmentDelete/{appointmentId}")
    public ResponseEntity<Object> DeleteAppointment( @PathVariable Long appointmentId) {

        try{
            crossService.deleteAppointment(appointmentId) ;
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }


    }

    /**
     * Get method for getting all locations.
     * @return response with Locations data
     */
    @GetMapping("/getLocations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locationList = donorService.getListOfLocations("null");

        if (locationList != null && !locationList.isEmpty()) {
            return ResponseEntity.ok(locationList);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }


}
