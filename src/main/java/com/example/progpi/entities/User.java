package com.example.progpi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// con lombok fa tutto solo
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private String email;
    private String surname;

    // utiliziamo maven per il costruttore e i get, usiamo il pachetto lombok,
    // per evitare di definire manualmante i metodi get set e costruttore
}
