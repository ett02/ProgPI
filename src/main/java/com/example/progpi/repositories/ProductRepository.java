package com.example.progpi.repositories;


import com.example.progpi.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findProductByID(int id);
    boolean existsByBarCode(String code);
    Product findProductByBarCode(String barCode);


}
