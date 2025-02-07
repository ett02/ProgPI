package com.example.progpi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    @Column (name = "id", unique = true)
    private int ID;

    @Basic
    @Column(name = "username", length = 70, nullable = false)
    private String username;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column (name = "name", length = 50)
    private String name;

    @Basic
    @Column (name = "email", length = 100, unique = true, nullable = false)
    private String email;

    @Basic
    @Column (name = "surname", length = 50)
    private String surname;

    @Basic
    @Column(name = "codFisc", unique = true )
    private String codFisc;

    @Basic
    @Column(name = "telephon", length = 10)
    private String telephon;

    @Basic
    @Column(name = "address", length = 180)
    private String address;

    //relazioni

    @OneToOne(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @JsonIgnore
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @ToString.Exclude
    private List<Storico> storicoList;

}
