package com.example.progpi.controllers;

import com.example.progpi.Utilities.Exception.NoConsistentQuantityException;
import com.example.progpi.entities.Product;
import com.example.progpi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController

@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/addUpdate")
    public ResponseEntity<Product> addProduct(@RequestPart(value= "product") Product pr, @RequestPart(value ="file", required =false)MultipartFile img) throws NoConsistentQuantityException {
            return new ResponseEntity(productService.addUpdateProduct(pr,img), HttpStatus.OK);
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
