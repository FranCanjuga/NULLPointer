package com.fer.progi.BloodDonation.funcionality.services;

import com.fer.progi.BloodDonation.funcionality.controllers.dto.ApointmentDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.AppointmentFinishedDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.AppointmentsResponseDTO;
import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.models.*;
import com.fer.progi.BloodDonation.funcionality.repositorys.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CrossService {

    private final CrossRepository crossRepository;

    private final LocationRepository locationRepository;

    private final BloodTypeRepository bloodTypeRepository;

    private final DonationHistoryRepository donationHistoryRepository;

    private final AkcijaKrvRepository akcijaKrvRepository;

    private final PriznajeRepository priznajeRepository;

    private final PriznanjaDonorRepository priznanjaDonorRepository;

    private final PotvrdeDonoraRepository potvrdeDonoraRepository;


    /**
     * Creates new appointment.
     * @param appointment appointment in ApointmentDTO format
     */
    public void addAppointment(ApointmentDTO appointment) {

        Location location = locationRepository.findById(appointment.locationID).orElse(null);
        if(location == null) {
            throw new IllegalArgumentException("Location with given ID does not exist");
        }

        LocalDateTime dateTime;
        try{
             dateTime = LocalDateTime.parse(appointment.getDateAndTime());
        } catch (Exception e) {
            throw new IllegalArgumentException("Date and time format is not valid");
        }

//TOO DOO - provjeriti da li je datum u buducnosti


//        -provjeri da nema konflikta u datumima na toj lokaciji

        Appointment appointmentModel = new Appointment();
        appointmentModel.setLocation(location);
        appointmentModel.setCriticalAction(appointment.isCriticalAction());
        appointmentModel.setDateAndTime(dateTime);
        crossRepository.save(appointmentModel);



        //Connect appointment with bloodTypes if appointment is critical
        if(appointment.isCriticalAction()){

            List<BloodType> bloodTypes =bloodTypeRepository.findByTypes(appointment.getBloodTypes());

            for (BloodType bloodType : bloodTypes) {
                AkcijaKrv akcijaKrv = new AkcijaKrv();
                akcijaKrv.setAppointment(appointmentModel);
                akcijaKrv.setBloodType(bloodType);
                akcijaKrvRepository.save(akcijaKrv);
            }

        }

    }


    /**
     * Returns all registered donors for given appointment.
     * @param appointmentId id of appointment in body
     * @return array of donors in DonorDTO format
     */
    public DonorDTO[] getRegisteredForAppointment(Long appointmentId) {
       var appointments = crossRepository.findById(appointmentId).
                  orElseThrow(() -> new IllegalArgumentException("Appointment with given ID does not exist"));
       var donors = donationHistoryRepository.findAll()
               .stream()
               .filter(donationHistory -> Objects.equals(donationHistory.getAppointment().getAppointment_id(), appointmentId))
               .map(donationHistory ->
                       new DonorDTO(donationHistory.getDonor())).toList();

       return donors.toArray(DonorDTO[]::new);

    }


    /**
     * Finishes appointment with given ID and marks donors with given usernames as came.
     * @param appointmentId id of appointment
     * @param usernames array of usernames of donors that came
     */
    public void finishAppointment(Long appointmentId, String[] usernames) {

            var donationHistories = donationHistoryRepository.findAll()
                    .stream()
                    .filter(donationHistory -> Objects.equals(donationHistory.getAppointment().getAppointment_id(), appointmentId)).toList();


        Appointment app  =  crossRepository.findById(appointmentId).orElse(null);
        if(app == null ){
            throw new IllegalArgumentException("Appointment with given ID does not exist");
        } else if (app.isFinished() || (app.getDateAndTime().isAfter(LocalDateTime.now())) ){
            throw new IllegalArgumentException("Appointment is already finished or is in the future");
        } else if (usernames.length > donationHistories.size()) {
            throw new IllegalArgumentException("Number of usernames is greater than number of registered donors");
        }


        app.setFinished(true);

        List<Priznanje>  priznanja = priznajeRepository.findAll();

            for (String username : usernames) {
                var donationHistory = donationHistories.stream()
                        .filter(donationHistory1 -> donationHistory1.getDonor().getUsername().equals(username)
                                && donationHistory1.getAppointment().getAppointment_id().equals(appointmentId))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Donor with given username is not registered for this appointment"));


                int numberOfDonations = countDonorDonations(username);
                for(var priznanje : priznanja){
                    if(numberOfDonations == priznanje.getCondition()){
                        PriznanjaDonora priznanjaDonora = new PriznanjaDonora();
                        priznanjaDonora.setDonationHistory(donationHistory);
                        priznanjaDonora.setPriznanje(priznanje);
                        priznanjaDonorRepository.save(priznanjaDonora);
                    }
                }
                potvrdeDonoraRepository.findByDontionHistoryId(donationHistory.getDonationHistory_id())
                          .forEach(potvrdeDonora -> {

                              // Get the current date and time and add 2 days
                              LocalDateTime localDateTime = LocalDateTime.now().plusDays(2);

                              // Convert LocalDateTime to Instant
                              Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

                              // Convert Instant to Date
                              Date date = Date.from(instant);
                              potvrdeDonora.setGiven(true);
                              //Date date = Date.from(Instant.from(LocalDateTime.now().plusDays(2)));
                              potvrdeDonora.setExpiers(date);
                              potvrdeDonoraRepository.save(potvrdeDonora);
                          });

                donationHistory.setCame(true);
                donationHistoryRepository.save(donationHistory);
                crossRepository.save(app);


            }

    }

    private int countDonorDonations(String username) {
        return donationHistoryRepository.findAll()
                .stream()
                .filter(donationHistory -> donationHistory.getDonor().getUsername().equals(username))
                .filter(DonationHistory::isCame).toList()
                .size();
    }

    /**
     * Returns all active appointments.
     * @return array active appointments
     */
    public AppointmentsResponseDTO[] getActiveAppointments() {

        return crossRepository.findAll()
                .stream()
                .filter(appointment -> !appointment.isFinished())
                .map(AppointmentsResponseDTO::new).toList()
                .toArray(AppointmentsResponseDTO[]::new);
    }

    /**
     * Deletes appointment with given ID.
     * @param appointmentID id of appointment
     */
    public void deleteAppointment(Long appointmentID) {

        Appointment appointment = crossRepository.findById(appointmentID).orElse(null);

        if(appointment == null) {
            throw new IllegalArgumentException("Appointment with given ID does not exist");
        }
        if(appointment.isFinished()) {
            throw new IllegalArgumentException("Appointment is already finished");
        }

        //delete potvrdaDonor

        List<PotvrdeDonora> potvrdeDonora = potvrdeDonoraRepository.findAll()
                .stream()
                .filter(potvrdaD -> Objects.equals(potvrdaD.getDonationHistory().getAppointment().getAppointment_id(), appointmentID))
                .toList();
        potvrdeDonoraRepository.deleteAll(potvrdeDonora);

        //delete akcija krv

        List<AkcijaKrv> akcijeKrvi = akcijaKrvRepository.findAll()
                .stream()
                .filter(akcijaKrv -> Objects.equals(akcijaKrv.getAppointment().getAppointment_id(), appointmentID))
                .toList();
        akcijaKrvRepository.deleteAll(akcijeKrvi);

        //delete donation history
        List<DonationHistory> donationHistories = donationHistoryRepository.findAll()
                .stream()
                .filter(donationHistory -> Objects.equals(donationHistory.getAppointment().getAppointment_id(), appointmentID))
                .toList();
        donationHistoryRepository.deleteAll(donationHistories);

        //delete appointment
        crossRepository.delete(appointment);

    }
}
