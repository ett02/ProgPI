package com.example.progpi.services;

import com.example.progpi.entities.Cart;
import com.example.progpi.entities.Product;
import com.example.progpi.entities.ProductInCart;
import com.example.progpi.entities.Users;
import com.example.progpi.repositories.CartRepository;
import com.example.progpi.repositories.ProductInCartRepository;
import com.example.progpi.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private ProductInCartRepository productInCartRepository;


    public List<Product> getProductbyUser(int u) {
        Cart cart = cartRepository.findByUserID(u);

        List<ProductInCart> PC=productInCartRepository.findAllByCartID(cart.getID());
        List<Product> ret= new ArrayList<>();
        for(ProductInCart productInCart: PC ){
            ret.add(productInCart.getProduct());
        }
        return ret;
    }


}
