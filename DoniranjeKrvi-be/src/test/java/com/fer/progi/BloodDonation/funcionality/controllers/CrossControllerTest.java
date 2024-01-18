package com.fer.progi.BloodDonation.funcionality.controllers;

import com.fer.progi.BloodDonation.funcionality.controllers.CrossControler;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.ApointmentDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.AppointmentFinishedDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.AppointmentsResponseDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.models.Location;
import com.fer.progi.BloodDonation.funcionality.services.CrossService;
import com.fer.progi.BloodDonation.funcionality.services.DonorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CrossControllerTest {

    @Mock
    private CrossService crossService;

    @Mock
    private DonorService donorService;

    @InjectMocks
    private CrossControler crossControler;

    @Test
    public void testGetRegisteredForAppointment() {
        Long appointmentId = 1L;
        DonorDTO[] donorDTOArray = new DonorDTO[]{new DonorDTO(
                "john_doe",
                new Date(1990-5-15),
                "Male",
                "A+",
                "New York",
                true,
                "John",
                "Doe",
                "555-1234",
                "Best Donor"
        ), new DonorDTO(
                "jane_smith",
                new Date(1985-8-25),
                "Female",
                "O-",
                "Los Angeles",
                false,
                "Jane",
                "Smith",
                "555-5678",
                "Top Donor"
        )};
        when(crossService.getRegisteredForAppointment(appointmentId)).thenReturn(donorDTOArray);

        DonorDTO[] result = crossControler.getRegisteredForAppointment(appointmentId);

        assertEquals(donorDTOArray, result);
        verify(crossService).getRegisteredForAppointment(appointmentId);
    }

    @Test
    public void testGetActiveAppointments() {
        AppointmentsResponseDTO[] appointmentsResponseDTOArray = new AppointmentsResponseDTO[]{
                new AppointmentsResponseDTO(
                        1L,
                        new Location(1L, "Hospital A", 40.7128, -74.0060),
                        new String[]{"A+", "B-"},
                        true,
                        LocalDateTime.of(2024, 1, 18, 10, 0),
                        false),
                new AppointmentsResponseDTO(
                        2L,
                        new Location(2L, "Clinic B", 34.0522, -118.2437),
                        new String[]{"O+", "AB-"},
                        false,
                        LocalDateTime.of(2024, 1, 20, 15, 30),
                        true)
        };
        when(crossService.getActiveAppointments()).thenReturn(appointmentsResponseDTOArray);

        AppointmentsResponseDTO[] result = crossControler.getActiveAppointments();

        assertEquals(appointmentsResponseDTOArray, result);
        verify(crossService).getActiveAppointments();
    }

    @Test
    public void testAddAppointment() {
        ApointmentDTO apointmentDTO = new ApointmentDTO(
                new String[]{"A+", "B-"},
                1L,
                true,
                "2024-01-20T14:30:00"
        );
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(null);
        doNothing().when(crossService).addAppointment(apointmentDTO);

        ResponseEntity<Object> result = crossControler.addAppointment(apointmentDTO);

        assertEquals(expectedResponse, result);
        verify(crossService).addAppointment(apointmentDTO);
    }

    @Test
    public void testAppointmentFinished() {
        AppointmentFinishedDTO request = new AppointmentFinishedDTO(new String[]{"username1", "username2"}, 1L);
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(null);


        ResponseEntity<Object> result = crossControler.AppointmentFinished(request);

        assertEquals(expectedResponse, result);
        verify(crossService).finishAppointment(request.getAppointmentID(), request.getUsernames());
    }

    @Test
    public void testDeleteAppointment() {
        Long appointmentId = 1L;
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(null);
        doNothing().when(crossService).deleteAppointment(appointmentId);

        ResponseEntity<Object> result = crossControler.DeleteAppointment(appointmentId);

        assertEquals(expectedResponse, result);
        verify(crossService).deleteAppointment(appointmentId);
    }

    @Test
    public void testGetAllLocations() {
        List<Location> locationList = List.of(
                new Location(1L, "Zagreb", 1, 1),
                new Location(2L, "Split", 1, 1),
                new Location(3L, "Osijek", 1, 1),
                new Location(4L, "Rijeka", 1, 1)
        );
        when(donorService.getListOfLocations("null")).thenReturn(locationList);

        ResponseEntity<List<Location>> result = crossControler.getAllLocations();

        if (locationList != null && !locationList.isEmpty()) {
            assertEquals(ResponseEntity.ok(locationList), result);
        } else {
            assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null), result);
        }
        verify(donorService).getListOfLocations("null");
    }
}