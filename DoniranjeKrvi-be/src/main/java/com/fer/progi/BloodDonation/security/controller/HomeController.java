package com.fer.progi.BloodDonation.security.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class HomeController {

    Map<String, String> userAuthority = new HashMap<>();

    @GetMapping("/")
    public String home(){
        return "Hello home";
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal OidcUser user){
        return String.format("Welcome %s", user);
    }

    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal OidcUser user){
        return String.format("Welcome %s", user);
    }

    @GetMapping("/openid")
    public String openid(@AuthenticationPrincipal OidcUser user){
        return String.format("Welcome %s", user);
    }
}
