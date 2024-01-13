package com.fer.progi.BloodDonation.funcionality.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Setter
@Entity
@Table(name = "users")
public class AppUser implements UserDetails {




    @Id
    @Column(unique = true)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String password;




    @Column(unique = true)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "userRole",
            joinColumns = {@JoinColumn(name = "username")},
            inverseJoinColumns = {@JoinColumn(name = "roleId")},
            uniqueConstraints = @UniqueConstraint(columnNames = {"username", "roleId"})
    )
    private Set<Role> authorities;

    public AppUser() {
    }
    public AppUser(AppUser user){
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.phoneNumber;
        this.password = user.getPassword();
        this.authorities = (Set<Role>) user.getAuthorities();

    }

    public AppUser( String username, String password, Set<Role> authorities) {
        super();
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public AppUser(String username, String firstName, String lastName, String phoneNumber, String password, Set<Role> authorities) {
        super();
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.authorities = authorities;
    }


    @Override
    public Set<Role> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
