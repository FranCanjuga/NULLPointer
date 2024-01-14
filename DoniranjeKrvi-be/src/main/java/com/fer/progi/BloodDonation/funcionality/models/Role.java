package com.fer.progi.BloodDonation.funcionality.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="roles")
@Setter
@Getter
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer role_id;

    private String authority;

    public Role() {
    }


    public Role(String authority) {
        this.authority = authority;
    }

    public Role(int roleId, String authority) {
        this.role_id = roleId;
        this.authority = authority;
    }

    public int getRoleId() {
        return role_id;
    }

    public void setRoleId(int roleId) {
        this.role_id = roleId;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
