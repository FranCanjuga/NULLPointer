package com.fer.progi.BloodDonation.funcionality.services;


import com.fer.progi.BloodDonation.funcionality.controllers.dto.DonorDTO;
import com.fer.progi.BloodDonation.funcionality.repositorys.DonorRepository;
import com.fer.progi.BloodDonation.funcionality.models.Donor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DonorService {
    @Autowired
    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public DonorDTO getDonorDataByUsername(String username) {
        Optional<Donor> opt =  donorRepository.findDonorByUsername(username);

        if(opt.isEmpty()){
            return null;
        }
        Donor donor = opt.get();

        return  new DonorDTO(donor.getUsername() , donor.getDateOfBirth(), donor.getGender(), donor.getBloodType(), donor.getLocation().getLocationName(), donor.isVerified(),
                donor.getAppUser().getFirstName(), donor.getAppUser().getLastName(), donor.getAppUser().getPhoneNumber());
    }
}
