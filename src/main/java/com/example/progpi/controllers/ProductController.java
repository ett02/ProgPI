package com.example.progpi.controllers;

import com.example.progpi.Utilities.Exception.NoConsistentQuantityException;
import com.example.progpi.entities.Product;
import com.example.progpi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PutMapping("/add")
    public Product addProduct(@RequestBody Product pr) throws NoConsistentQuantityException {
            return productService.addUpdateProduct(pr);
    }

    @GetMapping("/getProduct")
    public Product getProduct(@RequestParam("id") int id) throws Exception {
        return productService.getProduct(id);
    }

    @GetMapping("/esiste")
    public ResponseEntity<Boolean> Esiste(@RequestParam("barCode")String code) throws Exception {
        return new ResponseEntity(productService.Esiste(code), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public List<Product> getAllProducts(){
       return productService.getAllProducts();
    }

}
