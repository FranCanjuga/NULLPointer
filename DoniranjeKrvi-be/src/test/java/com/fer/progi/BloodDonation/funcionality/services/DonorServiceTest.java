package com.fer.progi.BloodDonation.funcionality.services;

import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonationHistoryDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.AppointmentGetDTO;
import com.fer.progi.BloodDonation.funcionality.models.*;
import com.fer.progi.BloodDonation.funcionality.repositorys.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DonorServiceTest {

    @Mock
    private DonorRepository donorRepository;

    @Mock
    private DonationHistoryRepository historyRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private BloodTypeRepository bloodTypeRepository;

    @Mock
    private PotvrdaRepository potvrdaRepository;

    @Mock
    private PotvrdeDonoraRepository potvrdeDonoraRepository;

    @Mock
    private PriznanjaDonoraRepository priznanjaDonoraRepository;

    @InjectMocks
    private DonorService donorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDonorDataByUsername_DonorExists_ReturnsDonorDTO() {
        // Arrange
        String username = "testUser";
        Date dateOfBirth = new Date();
        String gender = "Male";
        BloodType bloodType = new BloodType("AB+");
        Location location = new Location(1L, "TestLocation", 0.0, 0.0);
        boolean verified = true;

        Donor donor = new Donor(username, dateOfBirth, gender, bloodType, location, verified);
        donor.setAppUser(new AppUser("testUser", "firstName", "lastName", "1234", "password", new HashSet<>()));

        when(donorRepository.findDonorByUsername(username)).thenReturn(Optional.of(donor));

        // Act
        DonorDTO result = donorService.getDonorDataByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(dateOfBirth, result.getDateOfBirth());
        assertEquals(gender, result.getGender());
        assertEquals(bloodType.getType(), result.getBloodType());
        assertEquals(location.getLocationName(), result.getCity());
        assertEquals(verified, result.isVerified());
    }

    @Test
    void getDonorDataByUsername_DonorNotExists_ReturnsNull() {
        // Arrange
        String username = "nonexistentUser";

        when(donorRepository.findDonorByUsername(username)).thenReturn(Optional.empty());

        // Act
        DonorDTO result = donorService.getDonorDataByUsername(username);

        // Assert
        assertNull(result);
    }


    @Test
    void createNewReservation_InvalidAppointmentID_ReturnsNull() {
        // Arrange
        String username = "testUser";
        DonationHistoryDTO historyDTO = new DonationHistoryDTO();
        historyDTO.setUsername(username);
        historyDTO.setAppointmentID(1L);

        when(donorRepository.findByUsername(username)).thenReturn(new Donor());
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        DonationHistory result = donorService.createNewReservation(historyDTO);

        // Assert
        assertNull(result);
    }

    @Test
    void getDonationReservationByUsername_ValidUsername_ReturnsDonationHistoryDTOList() {
        // Arrange
        String username = "testUser";
        Donor donor = new Donor(username, new Date(), "Male", new BloodType("A+"), new Location(1L, "TestLocation", 0.0, 0.0), true);
        Location location = new Location(1L, "Zagreb", 1, 1);
        donor.setAppUser(new AppUser("testUser", "firstName", "lastName", "1234", "password", new HashSet<>()));
        when(donorRepository.findDonorByUsername(username)).thenReturn(Optional.of(donor));

        Appointment appointment1 = new Appointment();
        appointment1.setAppointment_id(1L);
        appointment1.setDateAndTime(LocalDateTime.now());

        appointment1.setFinished(false);
        DonationHistory history1 = new DonationHistory(donor, appointment1, false);
        appointment1.setDonationHistory(new HashSet<>());
        appointment1.getDonationHistory().add(history1);
        appointment1.setBloodTypes(new HashSet<>());
        appointment1.setCriticalAction(false);
        appointment1.setLocation(location);

        Appointment appointment2 = new Appointment();
        appointment2.setAppointment_id(2L);
        appointment2.setDateAndTime(LocalDateTime.now().minusDays(1));
        appointment2.setFinished(true);
        DonationHistory history2 = new DonationHistory(donor, appointment2, false);
        appointment2.setDonationHistory(new HashSet<>());
        appointment2.getDonationHistory().add(history2);
        appointment2.setBloodTypes(new HashSet<>());
        appointment2.setCriticalAction(false);
        appointment2.setLocation(location);


        when(historyRepository.findByDonor_id(donor.getDonor_id())).thenReturn(Arrays.asList(history1, history2));
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment1));
        when(appointmentRepository.findById(2L)).thenReturn(Optional.of(appointment2));

        // Act
        List<DonationHistoryDTO> result = donorService.getDonationReservationByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(appointment1.getAppointment_id(), result.get(0).getAppointmentID());
    }

    @Test
    void getAllDonationReservationByUsername_ValidUsername_ReturnsDonationHistoryDTOList() {
        // Arrange
        String username = "testUser";
        Location location = new Location(1L, "Zagreb", 1, 1);
        Donor donor = new Donor(username, new Date(), "Male", new BloodType("A+"), new Location(1L, "TestLocation", 0.0, 0.0), true);
        donor.setAppUser(new AppUser("testUser", "firstName", "lastName", "1234", "password", new HashSet<>()));
        when(donorRepository.findDonorByUsername(username)).thenReturn(Optional.of(donor));

        Appointment appointment1 = new Appointment();
        appointment1.setAppointment_id(1L);
        appointment1.setDateAndTime(LocalDateTime.now());

        appointment1.setFinished(false);
        DonationHistory history1 = new DonationHistory(donor, appointment1, false);
        appointment1.setDonationHistory(new HashSet<>());
        appointment1.getDonationHistory().add(history1);
        appointment1.setBloodTypes(new HashSet<>());
        appointment1.setCriticalAction(false);
        appointment1.setLocation(location);

        Appointment appointment2 = new Appointment();
        appointment2.setAppointment_id(2L);
        appointment2.setDateAndTime(LocalDateTime.now().minusDays(1));
        appointment2.setFinished(true);
        DonationHistory history2 = new DonationHistory(donor, appointment2, false);
        appointment2.setDonationHistory(new HashSet<>());
        appointment2.getDonationHistory().add(history2);
        appointment2.setBloodTypes(new HashSet<>());
        appointment2.setCriticalAction(false);
        appointment2.setLocation(location);

        when(historyRepository.findByDonor_id(donor.getDonor_id())).thenReturn(Arrays.asList(history1, history2));
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment1));
        when(appointmentRepository.findById(2L)).thenReturn(Optional.of(appointment2));

        // Act
        List<DonationHistoryDTO> result = donorService.getAllDonationReservationByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getListOfActiveDonationDates_HasActiveAppointments_ReturnsAppointmentGetDTOList() {
        // Arrange
        List<Appointment> activeAppointments = Arrays.asList(
                new Appointment(1L, new Location(), new HashSet<>(), new HashSet<>(), false, LocalDateTime.now().plusHours(1), false),
                new Appointment(2L, new Location(), new HashSet<>(), new HashSet<>(), false, LocalDateTime.now().plusDays(2), false)
        );
        when(appointmentRepository.findAll()).thenReturn(activeAppointments);

        // Act
        List<AppointmentGetDTO> result = donorService.getListOfActiveDonationDates("testUser");

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

}
