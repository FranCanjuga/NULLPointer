package com.fer.progi.BloodDonation.funcionality.services;

import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.models.AppUser;
import com.fer.progi.BloodDonation.funcionality.models.Donor;
import com.fer.progi.BloodDonation.funcionality.repositorys.DonorRepository;
import com.fer.progi.BloodDonation.security.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final DonorRepository donorRepository;
    private final AppUserRepository userRepository;

    public List<DonorDTO> getAllDonors() {
        return donorRepository.findAll()
                .stream()
                .map(DonorDTO::new)
                .toList();

    }

    public List<DonorDTO> getUnregisteredDonors() {
        List<Donor> donors = donorRepository.findAll();
        List<Donor> unregistered = new ArrayList<>();
        for (Donor donor : donors) {
            if (!donor.isVerified()) {
                unregistered.add(donor);
            }
        }
        return unregistered.stream().map(DonorDTO::new).toList();
    }

    public Donor getDonorByUsername(String username) {
        return donorRepository.findByUsername(username);
    }

    public boolean approveDonor(String username) {
        Donor donor = donorRepository.findByUsername(username);
        if (donor != null) {
            // Approve the donor, for example, set verified to true
            donor.setVerified(true);
            donorRepository.save(donor);
            return true;
        }
        return false;
    }

    public boolean rejectDonor(String username) {
        Donor donor = donorRepository.findByUsername(username);
        if (donor != null) {
            // Reject the donor, for example, remove the donor from the database
            donorRepository.delete(donor);
            userRepository.deleteAppUserByUsername(donor.getUsername());
            return true;
        }
        return false;
    }
}
