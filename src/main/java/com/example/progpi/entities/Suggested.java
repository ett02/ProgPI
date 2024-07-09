package com.example.progpi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name =  "suggested")
public class Suggested {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private int ID;

    @ManyToOne
    @JoinColumn(name = "uPrime")
    @JsonIgnore
    @ToString.Exclude
    private UserPrime usersP;

    @ManyToOne
    @JoinColumn(name = "prodSugg")
    @JsonIgnore
    @ToString.Exclude
    private Product product;




}
