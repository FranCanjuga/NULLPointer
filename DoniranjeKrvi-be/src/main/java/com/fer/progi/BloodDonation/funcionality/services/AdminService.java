package com.fer.progi.BloodDonation.funcionality.services;

import com.fer.progi.BloodDonation.funcionality.models.Donor;
import com.fer.progi.BloodDonation.funcionality.repositorys.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private DonorRepository donorRepository;

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public List<Donor> getUnregisteredDonors() {
        List<Donor> donors = donorRepository.findAll();
        List<Donor> unregistered = new ArrayList<>();
        for (Donor donor : donors) {
            if (!donor.isVerified()) {
                unregistered.add(donor);
            }
        }
        return unregistered;
    }

    public Donor getDonorByUsername(String username) {
        return donorRepository.findByUsername(username);
    }

    public void approveDonor(String username) {
        Donor donor = donorRepository.findByUsername(username);
        if (donor != null) {
            // Approve the donor, for example, set verified to true
            donor.setVerified(true);
            donorRepository.save(donor);
        }
    }

    public void rejectDonor(String username) {
        Donor donor = donorRepository.findByUsername(username);
        if (donor != null) {
            // Reject the donor, for example, remove the donor from the database
            donorRepository.delete(donor);
        }
    }
}
