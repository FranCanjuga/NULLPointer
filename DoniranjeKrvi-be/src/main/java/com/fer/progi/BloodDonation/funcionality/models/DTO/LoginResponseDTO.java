package com.fer.progi.BloodDonation.funcionality.models.DTO;

import com.fer.progi.BloodDonation.funcionality.models.AppUser;

public class LoginResponseDTO {

    private AppUser user;
    private String jwt;

    public LoginResponseDTO(){
        super();
    }

    public LoginResponseDTO(AppUser user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }

    public AppUser getUser() {
        return user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
