package com.example.progpi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name =  "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "name", length = 50)
    private String name;

    @Basic
    @Column(name = "barCode", length = 100, nullable = false)
    private String barCode;

    @Basic
    @Column(name = "description", length = 500)
    private String description;

    @Basic
    @Column(name = "price")
    private float price;

    @Basic
    @Column(name = "quantity")
    private int quantity;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @ToString.Exclude
    private List<Suggested> suggestedList;


}
