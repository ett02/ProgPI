package com.example.progpi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name =  "userPrime")
public class UserPrime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
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
    @Column(name = "codFisc", unique = true,nullable = false)
    private String codFisc;

    @Basic
    @Column(name = "telephon", nullable = false, length = 10)
    private String telephon;

    @Basic
    @Column(name = "addresss", length = 180)
    private String addresss;

    @Basic
    @Column(name = "sconto")
    private float sconto;

    @OneToMany(mappedBy = "userPrime")
    private List<Suggested> suggestedList;



}
