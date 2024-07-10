package com.example.progpi.entities;


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
@Table(name =  "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int ID;

    @OneToOne
    @ToString.Exclude
    @JoinColumn(name = "userS", nullable = false)
    private Users user;

    @OneToMany(mappedBy = "cart")
    @ToString.Exclude
    List<ProductInCart> listProductInCart;

}
