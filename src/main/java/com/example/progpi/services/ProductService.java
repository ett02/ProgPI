package com.example.progpi.services;

import com.example.progpi.Utilities.Exception.NoConsistentQuantityException;
import com.example.progpi.entities.Product;
import com.example.progpi.repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    EntityManager entityManager;


    @Transactional(readOnly = false, propagation= Propagation.REQUIRED)
    public Product addUpdateProduct(Product product) throws NoConsistentQuantityException {
        if (!productRepository.existsByBarCode(product.getBarCode())){
            if (product.getQuantity() < 0 )
                throw new NoConsistentQuantityException();
            productRepository.save(product);
        }else {
            Product product1 = productRepository.findProductByBarCode(product.getBarCode());
            int q=product.getQuantity() + product1.getQuantity();
            if (q < 0 )
                throw new NoConsistentQuantityException();
            product1.setQuantity(q);
            if(product.getPrice()<0)
                throw new RuntimeException(); // da fare nel controller con la maschera
            product1.setPrice(product.getPrice());
        }
        return productRepository.findProductByBarCode(product.getBarCode());
    }


    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Product getProduct(int id) throws Exception{
        return productRepository.findProductByID(id);
    }

    @Transactional(readOnly = true,  propagation= Propagation.REQUIRED)
    public boolean Esiste(@RequestParam("code" )String code) throws Exception{
        return productRepository.existsByBarCode(code);
    }



}
