package com.example.progpi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

// con lombok fa tutto solo
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id", nullable = false)
    private int ID;
    @Column (name = "first_Name")
    private String name;
    @Column (name = "email")
    private String email;
    @Column (name = "surname",nullable = false)
    private String surname;

    // utiliziamo maven per il costruttore e i get, usiamo il pachetto lombok,
    // per evitare di definire manualmante i metodi get set e costruttore
}
