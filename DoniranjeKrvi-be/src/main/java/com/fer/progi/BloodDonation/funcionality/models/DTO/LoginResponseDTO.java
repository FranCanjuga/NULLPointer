package com.fer.progi.BloodDonation.funcionality.models.DTO;

import com.fer.progi.BloodDonation.funcionality.models.AppUser;
import com.fer.progi.BloodDonation.funcionality.models.Role;

import java.util.List;

public class LoginResponseDTO {

    private String username;
    private String jwt;
    private List<Role> roles;
    public LoginResponseDTO(){
        super();
    }

    public LoginResponseDTO(AppUser user, String jwt) {
        this.username = user.getUsername();
        this.jwt = jwt;
        this.roles= (List<Role>) user.getAuthorities();
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
