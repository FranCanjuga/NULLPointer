package com.fer.progi.BloodDonation.funcionality.controllers;

import com.fer.progi.BloodDonation.funcionality.controllers.DonorController;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonationHistoryDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.models.*;
import com.fer.progi.BloodDonation.funcionality.services.CrossService;
import com.fer.progi.BloodDonation.funcionality.services.DonorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DonorControllerTest {

    @Mock
    private DonorService donorService;

    @Mock
    private CrossService crossService;

    @InjectMocks
    private DonorController donorController;

    @Test
    public void testGetDonorData() {
        String username = "john_doe";
        DonorDTO donorDTO = new DonorDTO("john_doe", null, null, null, null, true, null, null, null, null);
        when(donorService.getDonorDataByUsername(username)).thenReturn(donorDTO);

        ResponseEntity<DonorDTO> result = donorController.getDonorData(username);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(donorDTO, result.getBody());
        verify(donorService).getDonorDataByUsername(username);
    }

    @Test
    public void testGetDonorDataNotFound() {
        String username = "non_existing_user";
        when(donorService.getDonorDataByUsername(username)).thenReturn(null);

        ResponseEntity<DonorDTO> result = donorController.getDonorData(username);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(null, result.getBody());
        verify(donorService).getDonorDataByUsername(username);
    }

    @Test
    public void testCreateDonationReservation() {
        DonationHistoryDTO historyDTO = new DonationHistoryDTO();
        DonationHistory history = new DonationHistory();
        when(donorService.createNewReservation(historyDTO)).thenReturn(history);

        ResponseEntity<String> result = donorController.createDonationReservation(historyDTO);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Donation reservation created successfully.", result.getBody());
        verify(donorService).createNewReservation(historyDTO);
    }

    @Test
    public void testCreateDonationReservationError() {
        DonationHistoryDTO historyDTO = new DonationHistoryDTO();
        when(donorService.createNewReservation(historyDTO)).thenReturn(null);

        ResponseEntity<String> result = donorController.createDonationReservation(historyDTO);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("ERROR: Unable to create Donation reservation", result.getBody());
        verify(donorService).createNewReservation(historyDTO);
    }

    // Similar tests can be created for other methods in the DonorController class

}