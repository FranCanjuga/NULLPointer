package com.fer.progi.BloodDonation.funcionality.services;

import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.models.BloodType;
import com.fer.progi.BloodDonation.funcionality.models.Donor;
import com.fer.progi.BloodDonation.funcionality.models.Location;
import com.fer.progi.BloodDonation.funcionality.repositorys.DonorRepository;
import com.fer.progi.BloodDonation.security.repository.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private DonorRepository donorRepository;

    @Mock
    private AppUserRepository userRepository;

    @InjectMocks
    private AdminService adminService ;




    @Test
    public void getDonorByUsernameShouldReturnDonorForValidUsername() {
        Donor donor = new Donor("username1", new Date(), "Male", new BloodType(), new Location(), true);
        Mockito.when(donorRepository.findByUsername("username1")).thenReturn(donor);

        Donor result = adminService.getDonorByUsername("username1");

        Assertions.assertEquals(donor, result);
    }

    @Test
    public void getDonorByUsernameShouldReturnNullForInvalidUsername() {
        Mockito.when(donorRepository.findByUsername("invalidUsername")).thenReturn(null);

        Donor result = adminService.getDonorByUsername("invalidUsername");

        Assertions.assertNull(result);
    }

    @Test
    public void approveDonorShouldApproveDonorAndReturnTrue() {
        Donor donor = new Donor("username1", new Date(), "Male", new BloodType(), new Location(), true);
        Mockito.when(donorRepository.findByUsername("username1")).thenReturn(donor);
        Mockito.when(donorRepository.save(donor)).thenReturn(donor);

        boolean result = adminService.approveDonor("username1");

        Assertions.assertTrue(result);
        Assertions.assertTrue(donor.isVerified());
    }

    @Test
    public void approveDonorShouldReturnFalseForInvalidUsername() {
        Mockito.when(donorRepository.findByUsername("invalidUsername")).thenReturn(null);

        boolean result = adminService.approveDonor("invalidUsername");

        Assertions.assertFalse(result);
    }

    @Test
    public void rejectDonorShouldRejectDonorAndReturnTrue() {
        Donor donor = new Donor("username1", new Date(), "Male", new BloodType(), new Location(), false);
        Mockito.when(donorRepository.findByUsername("username1")).thenReturn(donor);
        Mockito.doNothing().when(donorRepository).delete(donor);
        Mockito.doNothing().when(userRepository).deleteAppUserByUsername(donor.getUsername());
        boolean result = adminService.rejectDonor("username1");

        Assertions.assertTrue(result);
    }

    @Test
    public void rejectDonorShouldReturnFalseForInvalidUsername() {
        Mockito.when(donorRepository.findByUsername("invalidUsername")).thenReturn(null);

        boolean result = adminService.rejectDonor("invalidUsername");

        Assertions.assertFalse(result);
    }
}