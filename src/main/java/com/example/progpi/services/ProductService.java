package com.example.progpi.services;

import com.example.progpi.entities.Product;
import com.example.progpi.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public boolean addProduct(Product product) {
        return productRepository.save(product) != null;
    }


}
