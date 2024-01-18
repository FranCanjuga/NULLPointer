package com.fer.progi.BloodDonation.funcionality.controllers.dto;

import com.fer.progi.BloodDonation.funcionality.models.Potvrda;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class PotvrdeDto {

    private int id;
    private String potvrdaName;

    public PotvrdeDto(Potvrda potvrda) {
        this.id = potvrda.getPotvrda_id();
        this.potvrdaName = potvrda.getNamePotvrda();
    }
}
