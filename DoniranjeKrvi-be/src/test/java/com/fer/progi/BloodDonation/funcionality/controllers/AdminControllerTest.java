package com.fer.progi.BloodDonation.funcionality.controllers;

import com.fer.progi.BloodDonation.funcionality.controllers.AdminController;
import com.fer.progi.BloodDonation.funcionality.models.Donor;
import com.fer.progi.BloodDonation.funcionality.services.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listAll_ReturnsListOfDonors() {
        // Arrange
        List<Donor> expectedDonors = Arrays.asList(new Donor(), new Donor());
        when(adminService.getAllDonors()).thenReturn(expectedDonors);

        // Act
        List<Donor> result = adminController.listAll();

        // Assert
        assertEquals(expectedDonors, result);
        verify(adminService, times(1)).getAllDonors();
    }

    @Test
    void listUnregistered_ReturnsListOfDonors() {
        // Arrange
        List<Donor> expectedDonors = Arrays.asList(new Donor(), new Donor());
        when(adminService.getUnregisteredDonors()).thenReturn(expectedDonors);

        // Act
        List<Donor> result = adminController.listUnregistered();

        // Assert
        assertEquals(expectedDonors, result);
        verify(adminService, times(1)).getUnregisteredDonors();
    }

    @Test
    void approveDonor_DonorApproved_ReturnsOk() {
        // Arrange
        String username = "testUser";
        when(adminService.approveDonor(username)).thenReturn(true);

        // Act
        ResponseEntity<Object> result = adminController.approveDonor(username);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());
        verify(adminService, times(1)).approveDonor(username);
    }

    @Test
    void approveDonor_DonorNotApproved_ReturnsBadRequest() {
        // Arrange
        String username = "testUser";
        when(adminService.approveDonor(username)).thenReturn(false);

        // Act
        ResponseEntity<Object> result = adminController.approveDonor(username);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNull(result.getBody());
        verify(adminService, times(1)).approveDonor(username);
    }

    @Test
    void rejectDonor_DonorRejected_ReturnsOk() {
        // Arrange
        String username = "testUser";
        when(adminService.rejectDonor(username)).thenReturn(true);

        // Act
        ResponseEntity<Object> result = adminController.rejectDonor(username);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());
        verify(adminService, times(1)).rejectDonor(username);
    }

    @Test
    void rejectDonor_DonorNotRejected_ReturnsBadRequest() {
        // Arrange
        String username = "testUser";
        when(adminService.rejectDonor(username)).thenReturn(false);

        // Act
        ResponseEntity<Object> result = adminController.rejectDonor(username);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNull(result.getBody());
        verify(adminService, times(1)).rejectDonor(username);
    }
}
