package com.fer.progi.BloodDonation.funcionality.repositorys;


import com.fer.progi.BloodDonation.funcionality.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository

public interface BloodTypeRepository extends JpaRepository<BloodType, Long>{


    /**
     * Method for finding bloodType entitys  by there types (A , B , AB , O ...)
     * @param bloodTypes - array of String names of bloodTypes
     * @return List of bloodTypes
     */

    default List<BloodType> findByTypes(String[] bloodTypes){

        var bloodTypesSet = Arrays.stream(bloodTypes)
                .collect(Collectors.toSet());

        var allBloodTypes = findAll();
        return allBloodTypes.stream()
                .filter(bloodType -> {
                    return bloodTypesSet.contains(bloodType.getType());
                })
                .collect(Collectors.toList());
    }


    @Override
    List<BloodType> findAll();

    BloodType findByType(String bloodType);

}
