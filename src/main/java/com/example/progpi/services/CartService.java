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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = false, propagation= Propagation.REQUIRED)
    public Cart addProd(List<Product> productList,  Users user) throws Exception{
        Cart c = (Cart) cartRepository.findCartByUserID(user.getID());
        if(c.getListProductInCart()==null){
            c.setListProductInCart(new ArrayList<ProductInCart>());
            cartRepository.save(c);
        }
        for (Product product : productList) {
            if(product.getPrice()!= productRepository.findProductById(product.getId()).getPrice()) {
                throw new RuntimeException("prezzo cambiato");// da fare
            }
            if(productInCartRepository.findProductByID(product.getId())){
                ProductInCart prod = productInCartRepository.findByProductID(product.getId());
                Product pr =productRepository.findProductById(product.getId());
                prod.setQuantity(product.getQuantity()+1);
            }else{
                ProductInCart pc = new ProductInCart();
                pc.setProduct(product);
                pc.setCart(c);
                pc.setQuantity(1);
            }
        }
        return c;
    }


    @Transactional(readOnly = true, propagation= Propagation.REQUIRED)
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
