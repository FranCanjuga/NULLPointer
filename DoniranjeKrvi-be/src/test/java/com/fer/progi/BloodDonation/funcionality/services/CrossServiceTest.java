package com.fer.progi.BloodDonation.funcionality.services;

import com.fer.progi.BloodDonation.funcionality.controllers.dto.ApointmentDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.AppointmentsResponseDTO;
import com.fer.progi.BloodDonation.funcionality.models.*;
import com.fer.progi.BloodDonation.funcionality.repositorys.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrossServiceTest {

    @Mock
    private CrossRepository crossRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private BloodTypeRepository bloodTypeRepository;

    @Mock
    private DonationHistoryRepository donationHistoryRepository;

    @Mock
    private AkcijaKrvRepository akcijaKrvRepository;

    @Mock
    private PriznajeRepository priznajeRepository;

    @Mock
    private PriznanjaDonorRepository priznanjaDonorRepository;

    @Mock
    private PotvrdeDonoraRepository potvrdeDonoraRepository;

    @InjectMocks
    private CrossService crossService;

    @Test
    void addAppointment_ValidAppointment_ShouldSaveAppointment() {
        // Arrange
        ApointmentDTO appointmentDTO = new ApointmentDTO();
        appointmentDTO.setLocationID(1L);
        appointmentDTO.setCriticalAction(false);
        appointmentDTO.setDateAndTime("2024-01-17T10:00:00");
        Location location = new Location();
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        when(crossRepository.save(any(Appointment.class))).thenReturn(new Appointment());

        // Act
        crossService.addAppointment(appointmentDTO);

        // Assert
        verify(crossRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void addAppointment_InvalidLocationId_ShouldThrowException() {
        // Arrange
        ApointmentDTO appointmentDTO = new ApointmentDTO();
        appointmentDTO.setLocationID(1L);
        appointmentDTO.setCriticalAction(false);
        appointmentDTO.setDateAndTime("2024-01-17T10:00:00");

        when(locationRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> crossService.addAppointment(appointmentDTO));
        verify(crossRepository, never()).save(any(Appointment.class));
    }


    @Test
    void finishAppointment_AppointmentAlreadyFinished_ShouldThrowException() {
        // Arrange
        Long appointmentId = 1L;
        Appointment appointment = new Appointment(appointmentId, new Location(1L, "Zagreb", 1.0, 1.0), new HashSet<>(), new HashSet<>(), false, LocalDateTime.now(), false);
        appointment.setFinished(true);
        when(crossRepository.findById(appointmentId)).thenReturn(java.util.Optional.of(appointment));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> crossService.finishAppointment(appointmentId, new String[]{"user1", "user2"}));
        verify(donationHistoryRepository, never()).save(any(DonationHistory.class));
        verify(potvrdeDonoraRepository, never()).save(any(PotvrdeDonora.class));
    }

    @Test
    void finishAppointment_AppointmentInFuture_ShouldThrowException() {
        // Arrange
        Long appointmentId = 1L;
        Appointment appointment = new Appointment(appointmentId, new Location(1L, "Zagreb", 1.0, 1.0), new HashSet<>(), new HashSet<>(), false, LocalDateTime.now(), false);
        appointment.setDateAndTime(LocalDateTime.now().plusDays(1));
        when(crossRepository.findById(appointmentId)).thenReturn(java.util.Optional.of(appointment));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> crossService.finishAppointment(appointmentId, new String[]{"user1", "user2"}));
        verify(donationHistoryRepository, never()).save(any(DonationHistory.class));
        verify(potvrdeDonoraRepository, never()).save(any(PotvrdeDonora.class));
    }

    @Test
    void finishAppointment_InvalidUsername_ShouldThrowException() {
        // Arrange
        Long appointmentId = 1L;
        Appointment appointment = new Appointment(appointmentId, new Location(1L, "Zagreb", 1.0, 1.0), new HashSet<>(), new HashSet<>(), false, LocalDateTime.now(), false);
        when(crossRepository.findById(appointmentId)).thenReturn(java.util.Optional.of(appointment));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> crossService.finishAppointment(appointmentId, new String[]{"user1", "user2"}));
        verify(donationHistoryRepository, never()).save(any(DonationHistory.class));
        verify(potvrdeDonoraRepository, never()).save(any(PotvrdeDonora.class));
    }


    @Test
    void getActiveAppointments_ShouldReturnActiveAppointments() {
        when(crossRepository.findAll()).thenReturn(Arrays.asList(
                new Appointment(1L, new Location(1L, "Zagreb", 1.0, 1.0), new HashSet<>(), new HashSet<>(), false, LocalDateTime.now().plusDays(1), false),
                new Appointment(2L, new Location(1L, "Zagreb", 1.0, 1.0), new HashSet<>(), new HashSet<>(), false, LocalDateTime.now().minusDays(1), true),
                new Appointment(3L, new Location(1L, "Zagreb", 1.0, 1.0), new HashSet<>(), new HashSet<>(), false, LocalDateTime.now().plusDays(2), false)
        ));

        // Act
        AppointmentsResponseDTO[] activeAppointments = crossService.getActiveAppointments();

        // Assert
        assertEquals(2, activeAppointments.length);
    }

    @Test
    void deleteAppointment_ValidAppointmentId_ShouldDeleteAppointment() {
        // Arrange
        Long appointmentId = 1L;
        Appointment appointment = new Appointment(1L, new Location(1L, "Zagreb", 1.0, 1.0), new HashSet<>(), new HashSet<>(), false, LocalDateTime.now().plusDays(1), false);
        when(crossRepository.findById(appointmentId)).thenReturn(java.util.Optional.of(appointment));

        // Act
        crossService.deleteAppointment(appointmentId);

        // Assert
        verify(potvrdeDonoraRepository, times(1)).deleteAll(anyList());
        verify(akcijaKrvRepository, times(1)).deleteAll(anyList());
        verify(donationHistoryRepository, times(1)).deleteAll(anyList());
        verify(crossRepository, times(1)).delete(any(Appointment.class));
    }

    @Test
    void deleteAppointment_AppointmentAlreadyFinished_ShouldThrowException() {
        // Arrange
        Long appointmentId = 1L;
        Appointment appointment = new Appointment(1L, new Location(1L, "Zagreb", 1.0, 1.0), new HashSet<>(), new HashSet<>(), false, LocalDateTime.now().plusDays(1), false);
        appointment.setFinished(true);
        when(crossRepository.findById(appointmentId)).thenReturn(java.util.Optional.of(appointment));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> crossService.deleteAppointment(appointmentId));
        verify(potvrdeDonoraRepository, never()).deleteAll(anyList());
        verify(akcijaKrvRepository, never()).deleteAll(anyList());
        verify(donationHistoryRepository, never()).deleteAll(anyList());
        verify(crossRepository, never()).delete(any(Appointment.class));
    }

}