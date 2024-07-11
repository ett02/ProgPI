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
import java.util.Optional;

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
        Cart c = cartRepository.findCartByUserID(user.getID());
        List<ProductInCart> listp=c.getListProductInCart();

        for (Product product : productList) {

            if (!productRepository.existsByBarCode(product.getBarCode())) {
                throw new RuntimeException("prod non esiste");
            }
           /* if (product.getPrice() != productRepository.findProductByBarCode(product.getBarCode()).getPrice()) { // se il prezzo è cambiato
                throw new PriceChangedException();// da fare
            }

            */
            Product p = productRepository.findProductByBarCode(product.getBarCode());
            if (!productInCartRepository.existsByID(p.getID())) {//
                //altrimenti lo aggiungo
                ProductInCart pc = new ProductInCart();
                pc.setCart(c);
                pc.setProduct(p);
                pc.setQuantity(product.getQuantity());
                chek(product);
                listp.add(pc);
                productInCartRepository.save(pc);

            } else {
                chek(product);
                ProductInCart pc = productInCartRepository.findByProductID(p.getID());
                pc.setQuantity(product.getQuantity() + pc.getQuantity());

            }
        }

        return c;
    }

    @Transactional(readOnly = false, propagation= Propagation.REQUIRED)
    public void chek(Product pro) throws QuantityNotAvaibleException {
        Product p = productRepository.findProductByBarCode(pro.getBarCode());
        if (p.getQuantity() - pro.getQuantity() < 0) {
            throw new QuantityNotAvaibleException();
        } else {// setta la quantità disponibile aggiornandola
            p.setQuantity(p.getQuantity() - pro.getQuantity());
        }
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