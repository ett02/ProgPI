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
    @JsonIgnore
    @ToString.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id", nullable = false, unique = true)
    private int ID;

    @Basic
    @Column(name = "nikname", length = 70, nullable = false)
    private String nikname;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column (name = "name", length = 50, nullable = false)
    private String name;

    @Basic
    @Column (name = "email", length = 100, unique = true, nullable = false)
    private String email;

    @Basic
    @Column (name = "surname", length = 50,nullable = false)
    private String surname;

    @Basic
    @Column(name = "codFisc", unique = true, nullable = false)
    private String codFisc;

    @Basic
    @Column(name = "telephon", nullable = false, length = 10)
    private String telephon;

    @Basic
    @Column(name = "address", length = 180,nullable = false)
    private String address;



    @OneToOne(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @JsonIgnore
    private Cart cart;

}
