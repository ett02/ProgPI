package com.example.progpi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name =  "productInCart")
public class ProductInCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int ID;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product")
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart")
    private Cart cart;







}
