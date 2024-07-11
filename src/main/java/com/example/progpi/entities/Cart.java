package com.example.progpi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name =  "cart")
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int ID;

    @OneToOne
    @JoinColumn(name = "relatUser" )
    private Users user;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.REMOVE)
    List<ProductInCart> listProductInCart;

}
