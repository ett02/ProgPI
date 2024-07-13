package com.example.progpi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name =  "product")
public class Product {

    @Id
    @JsonIgnore
    @ToString.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int ID;

    @Basic
    @Column(name = "name", length = 50)
    private String name;

    @Basic
    @Column(name = "barCode", length = 100, nullable = false, unique = true)
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


    //relazioni

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    List<ProductInCart> listProductInCart;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    List<Storico> storicoList;

}
