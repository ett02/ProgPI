package com.example.progpi.repositories;

import com.example.progpi.entities.Product;
import com.example.progpi.entities.ProductInCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInCartRepository extends JpaRepository<ProductInCart, Integer>{

    boolean findProductByID(int id);
    ProductInCart findByProductID(int id);
    List<ProductInCart> findAllByCartID(int cartId);


}