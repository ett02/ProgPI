package com.example.progpi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id", nullable = false, unique = true)
    private int ID;

    @Basic
    @Column (name = "first_Name", length = 50)
    private String name;

    @Basic
    @Column (name = "email", length = 100)
    private String email;

    @Basic
    @Column (name = "surname", length = 50)
    private String surname;

    @Basic
    @Column(name = "codFisc", unique = true, nullable = false)
    private String codFisc;

    @Basic
    @Column(name = "telephon", nullable = false, length = 10)
    private String telephon;

    @Basic
    @Column(name = "addresss", length = 180)
    private String addresss;


    @OneToOne
    @ToString.Exclude
    private Cart cart;




    // utiliziamo maven per il costruttore e i get, usiamo il pachetto lombok,
    // per evitare di definire manualmante i metodi get set e costruttore
}
