package com.example.progpi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name =  "productIncart")
public class ProductInCart implements Serializable {
    @Id
    @JsonIgnore
    @ToString.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int ID;

    @Basic
    private int quantity;

    //relazioni


    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "related_cart")
    private Cart cart;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "related_product")
    private Product product;




}
