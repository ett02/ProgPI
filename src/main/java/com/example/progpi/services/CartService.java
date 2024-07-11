package com.example.progpi.services;

import com.example.progpi.Utilities.Exception.*;
import com.example.progpi.entities.Cart;
import com.example.progpi.entities.Product;
import com.example.progpi.entities.ProductInCart;
import com.example.progpi.entities.Users;
import com.example.progpi.repositories.CartRepository;
import com.example.progpi.repositories.ProductInCartRepository;
import com.example.progpi.repositories.ProductRepository;
import com.example.progpi.repositories.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
    @Autowired
    private UsersRepository UsersRepository;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private UsersRepository usersRepository;

    @Transactional(readOnly = false, propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
    public Cart addProd(List<Product> productList,  String cF) throws UserNotFoundException, PriceChangedException, QuantityNotAvaibleException{
        Users user =  usersRepository.findByCodFisc(cF);
        if(!usersRepository.existsById(user.getID())){
            throw new UserNotFoundException();
        }
        Cart c = (Cart) cartRepository.findCartByUserID(user.getID());
        if(c.getListProductInCart()==null){
            c.setListProductInCart(new ArrayList<ProductInCart>());
            cartRepository.save(c);
        }
        for (Product product : productList) {
            if(product.getPrice()!= productRepository.findProductById(product.getId()).getPrice()) { // se il prezzo è cambiato
                throw new PriceChangedException();// da fare
            }
            if(productInCartRepository.findProductByID(product.getId())){// se è già presente del carrello aumento la quantità
                ProductInCart pc = productInCartRepository.findByProductID(product.getId());
                Product pr =productRepository.findProductById(product.getId());
                pc.setQuantity(product.getQuantity()+1);
                productInCartRepository.save(pc);
            }else{//altrimenti lo aggiungo
                ProductInCart pc = new ProductInCart();
                pc.setProduct(product);
                pc.setCart(c);
                pc.setQuantity(1);
                productInCartRepository.save(pc);
            }
            for (ProductInCart pc : c.getListProductInCart()) {//controlliamo la quanità
                if(productRepository.findProductById(pc.getID()).getQuantity()-pc.getQuantity()<0){
                    throw new QuantityNotAvaibleException();
                }else {// setta la quantità disponibile aggiornandola
                    Product product1 = productRepository.findProductById(pc.getProduct().getId());
                    product1.setQuantity(product1.getQuantity()- pc.getQuantity());
                    productRepository.save(product1);
                    entityManager.merge(product1);
                }
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
