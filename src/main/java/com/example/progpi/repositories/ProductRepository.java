package com.example.progpi.repositories;


import com.example.progpi.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findProductByID(int id);
    boolean existsProductByBarCode(String code);
    Product findProductByBarCode(String barCode);
    List<Product> findProductByName(String barCode);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    Page<Product> findAllByName(String name, PageRequest pageRequest);
}
