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
    private UsersRepository usersRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean chekOut(List<Product> productList, String cF) throws UserNotFoundException, PriceChangedException, QuantityNotAvaibleException {
        Users user = usersRepository.findByCodFisc(cF);
        if (!usersRepository.existsById(user.getID())) {
            throw new UserNotFoundException();
        }
        Cart c = cartRepository.findCartByUserID(user.getID());
        List<ProductInCart> listp = c.getListProductInCart();

        for (Product product : productList) {

            if (!productRepository.existsByBarCode(product.getBarCode())) {
                throw new RuntimeException("prod non esiste");
            }
            if (product.getPrice() != productRepository.findProductByBarCode(product.getBarCode()).getPrice()) { // se il prezzo è cambiato
                throw new PriceChangedException();// da fare
            }
            Product p = productRepository.findProductByBarCode(product.getBarCode());
            if (productInCartRepository.existsByID(p.getID())) {//see è nel carrello
                chek(product);
            }
        }
        // eliminare la lista dei prodotti acquistati
        productInCartRepository.deleteAll(listp);
        c.setListProductInCart(new ArrayList<>());

        return true;
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Cart addProduc(List<Product> productList, String cF) throws UserNotFoundException, PriceChangedException, QuantityNotAvaibleException {
        Users user = usersRepository.findByCodFisc(cF);
        if (!usersRepository.existsById(user.getID())) {
            throw new UserNotFoundException();
        }
        Cart c = cartRepository.findCartByUserID(user.getID());
        List<ProductInCart> listp = c.getListProductInCart();
        for (Product product : productList) {
            if (!productRepository.existsByBarCode(product.getBarCode())) {
                throw new RuntimeException("prod non esiste");
            }
            if (product.getPrice() != productRepository.findProductByBarCode(product.getBarCode()).getPrice()) {
                throw new PriceChangedException();
            }
            Product product1 = productRepository.findProductByBarCode(product.getBarCode());
            if (!productInCartRepository.existsById(product1.getID())) {
                ProductInCart productInCart = new ProductInCart();
                productInCart.setProduct(product1);
                productInCart.setCart(c);
                productInCart.setQuantity(product.getQuantity());
                listp.add(productInCart);
                quantityAvainlecheck(product);
                productInCartRepository.save(productInCart);
            } else {
                ProductInCart product2 = productInCartRepository.findByProductID(product.getID());
                product2.setQuantity(product2.getQuantity() + product.getQuantity());
                quantityAvainlecheck(product);
            }
        }
        return c;
    }

    private void quantityAvainlecheck(Product pc) throws QuantityNotAvaibleException {
        Product product = productRepository.findProductByBarCode(pc.getBarCode());
        if (product.getQuantity() - pc.getQuantity() < 0){
            throw new QuantityNotAvaibleException();
        }
    }

    private void chek(Product pro) throws QuantityNotAvaibleException {
        Product p = productRepository.findProductByBarCode(pro.getBarCode());
        if (p.getQuantity() - pro.getQuantity() < 0) {
            throw new QuantityNotAvaibleException();
        } else {// setta la quantità disponibile aggiornandola
            p.setQuantity(p.getQuantity() - pro.getQuantity());
        }
    }

    @Transactional(readOnly = true, propagation= Propagation.REQUIRED)
    public List<ProductInCart> getProductbyUser(String codF) throws QuantityNotAvaibleException {
        Users u = usersRepository.findByCodFisc(codF);
        Cart cart = cartRepository.findByUserID(u.getID());
        List<ProductInCart> PC=productInCartRepository.findAllByCartID(cart.getID());
        for(ProductInCart productInCart: PC ){
            if (productInCart.getProduct().getQuantity()-productInCart.getQuantity()<0)
                throw new QuantityNotAvaibleException("Quantity :"+ productInCart.getProduct().getQuantity());
        }
        return PC;
    }
}