package com.fer.progi.BloodDonation.funcionality.models.DTO;

import com.fer.progi.BloodDonation.funcionality.models.AppUser;

public class LoginResponseDTO {

    private String username;
    private String jwt;

    public LoginResponseDTO(){
        super();
    }

    public LoginResponseDTO(AppUser user, String jwt) {
        this.username = user.getUsername();
        this.jwt = jwt;
    }

    public String getUsername() {
        return username;
    }

    public String getJwt() {
        return jwt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
