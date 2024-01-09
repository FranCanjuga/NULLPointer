package com.fer.progi.BloodDonation.funcionality.repositorys;


import com.fer.progi.BloodDonation.funcionality.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface BloodTypeRepository extends JpaRepository<BloodType, Long>{


    /**
     * Method for finding bloodType entitys  by there types (A , B , AB , O ...)
     * @param bloodTypes - array of String names of bloodTypes
     * @return List of bloodTypes
     */
    default List<BloodType> findByType(String[] bloodTypes){
        var bloodTypesSet = Arrays.stream(bloodTypes)
                .collect(Collectors.toSet());

        var allBloodTypes = findAll();
        return allBloodTypes.stream()
                .filter(bloodType -> {
                    return bloodTypesSet.contains(bloodType.getType());
                })
                .collect(Collectors.toList());
    }
}
