package com.example.progpi.repositories;

import com.example.progpi.entities.Product;
import com.example.progpi.entities.ProductInCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInCartRepository extends JpaRepository<ProductInCart, Integer>{

    boolean existsByProductID(int id);

    ProductInCart findByProductIDAndCartID(int productID, int cartID);
    boolean existsByProductIDAndCartID(int id, int cartID);
    ProductInCart findByProductID(int id);
    ProductInCart findByCartID( int cartID);
    @Query("SELECT p FROM ProductInCart p WHERE p.cart.ID = :id")
    Page<ProductInCart> findAllByCartID(@Param("id") int ID, Pageable pageable);

    List<ProductInCart> findAllProductByCartID(int cartID);
}
