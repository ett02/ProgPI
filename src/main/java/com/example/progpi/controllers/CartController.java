package com.example.progpi.controllers;

import com.example.progpi.entities.Product;
import com.example.progpi.entities.ProductInCart;
import com.example.progpi.entities.Users;
import com.example.progpi.repositories.ProductInCartRepository;
import com.example.progpi.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductInCartRepository productInCartRepository;


    @GetMapping("/getAllProd")
    public List<Product> getAllProducts(@RequestParam int id) {
        return cartService.getProductbyUser(id);
    }

}
