package com.example.progpi.controllers;

import com.example.progpi.entities.Product;
import com.example.progpi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/add")
    public boolean addProduct(@RequestBody Product pr) {
        return productService.addProduct(pr);
    }
}
