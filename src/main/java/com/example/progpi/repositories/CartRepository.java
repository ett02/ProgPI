package com.example.progpi.repositories;

import com.example.progpi.entities.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {


    Cart findByUserID(int userId);
    Cart findCartByID(int id);
    Cart findCartByUserID(int userId);

}
