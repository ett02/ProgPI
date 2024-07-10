package com.example.progpi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name =  "productIncart")
public class ProductInCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int ID;

    @ManyToOne
    @JoinColumn(name = "related_cart")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "related_product")
    private Product product;




}
