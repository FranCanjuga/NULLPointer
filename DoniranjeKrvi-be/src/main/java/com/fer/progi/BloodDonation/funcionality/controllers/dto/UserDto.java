package com.fer.progi.BloodDonation.funcionality.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.jwt.Jwt;


@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Jwt token;

    private String username;


}
