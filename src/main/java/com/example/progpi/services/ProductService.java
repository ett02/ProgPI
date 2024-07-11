package com.example.progpi.services;

import com.example.progpi.entities.Product;
import com.example.progpi.repositories.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Transactional(readOnly = false, propagation= Propagation.REQUIRED)
    public Product addProduct(Product product) throws Exception{
        if(productRepository.existsProductByBarCode(product.getBarCode())){
            throw new Exception("Product already exists");
        }else{
            productRepository.save(product);
        }
        return  product;
    }


}
