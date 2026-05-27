package com.shaoume.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @PostMapping("/api/register")
    public String register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password
    ) {

        System.out.println("Nom : " + name);
        System.out.println("Email : " + email);
        System.out.println("Password : " + password);

        return "Utilisateur enregistré avec succès";
    }
}