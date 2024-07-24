package com.example.progpi.services;

import com.example.progpi.Utilities.Exception.NoConsistentQuantityException;
import com.example.progpi.entities.Product;
import com.example.progpi.repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Transactional(readOnly = false, propagation= Propagation.REQUIRED)
    public Product addUpdateProduct(Product product,  MultipartFile file) throws NoConsistentQuantityException {
        if (!productRepository.existsProductByBarCode(product.getBarCode())){
            if (product.getQuantity() < 0 )
                throw new NoConsistentQuantityException();
            if (file != null && !file.isEmpty()) {
                try {
                    System.out.println(file.getBytes());
                    product.setImmage(file.getBytes());
                } catch (Exception e) {
                    throw new RuntimeException("Error saving image", e);
                }
            }
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
    public List<Product> getProduct(String nome) throws Exception{
        return productRepository.findProductByName(nome);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Page<Product> getAllProduct(int nPage, int dPage)throws Exception{
        PageRequest pageRequest = PageRequest.of(nPage, dPage);
        return productRepository.findAll(pageRequest);
    }

    @Transactional(readOnly = true,  propagation= Propagation.REQUIRED)
    public boolean Esiste(String code) throws Exception{
        return productRepository.existsProductByBarCode(code);
    }


    @Transactional(readOnly = true)
    public Page<Product> getAllByName(String name,int nPage, int dPage) throws Exception{
        PageRequest pageRequest = PageRequest.of(nPage, dPage);
        return productRepository.findAllByName(name,pageRequest);
    }

}
