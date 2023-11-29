package com.fer.progi.BloodDonation.funcionality.models.DTO;

import com.fer.progi.BloodDonation.funcionality.models.AppUser;
import com.fer.progi.BloodDonation.funcionality.models.Role;

import java.util.List;
import java.util.Set;

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
        roles= user.getAuthorities().stream().toList();
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


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
