package com.fer.progi.BloodDonation.funcionality.repositorys;


import com.fer.progi.BloodDonation.funcionality.models.Donor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.Assert;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection =  EmbeddedDatabaseConnection.H2)
public class DonorRepositoryTest {

    @Autowired
    private DonorRepository donorRepository;



    @Test
    public void DonorRepository_testSaveDonor() {
        Donor donor = Donor.builder().username("maja@gmail.com").gender("M").build();

        donorRepository.save(donor);

        List<Donor> donors = donorRepository.findAll();
        Assertions.assertEquals(1, donors.size());
        Assertions.assertEquals("maja@gmail.com", donors.get(0).getUsername());
        Assertions.assertEquals("M", donors.get(0).getGender());
    }

    @Test
    public void testFindDonorByUsername() {
        Donor donor = Donor.builder().username("maja@gmail.com").gender("M").build();
        donorRepository.save(donor);

        Donor foundDonor = donorRepository.findDonorByUsername("maja@gmail.com").get();
        Assertions.assertNotNull(foundDonor);
        // Add more assertions based on your expectations for the found donor
    }
}
