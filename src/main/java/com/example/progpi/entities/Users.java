package com.example.progpi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id", nullable = false, unique = true)
    private int ID;

    @Basic
    @Column (name = "name", length = 50)
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
    @Column(name = "address", length = 180)
    private String address;


    @OneToOne(cascade = CascadeType.MERGE)
    @ToString.Exclude
    @JsonIgnore
    private Cart cart;

}
