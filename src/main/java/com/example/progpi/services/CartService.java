package com.example.progpi.services;

import com.example.progpi.Utilities.Exception.*;
import com.example.progpi.entities.*;
import com.example.progpi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
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
    @Autowired
    private StoricoRepository storicoRepository;


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Product> chekOut(List<Product> productList, String email) throws UserNotFoundException, PriceChangedException, QuantityNotAvaibleException {
        Users user = usersRepository.findByEmail(email);
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
            ProductInCart productInCart =productInCartRepository.findByProductID(p.getID());

            if(productInCart.getQuantity()-product.getQuantity()==0){
                createStorico(user,product.getQuantity(),p);
                productInCartRepository.delete(productInCart);
            }else{
                productInCart.setQuantity(productInCart.getQuantity()-product.getQuantity());
                createStorico(user,product.getQuantity(),p);
            }
        }
        // eliminare la lista dei prodotti acquistati
        return productList;
    }


    private void createStorico(Users user, int quantity, Product p){
        Storico storico =  new Storico();
        storico.setUser(user);
        storico.setProduct(p);
        storico.setTime(Calendar.getInstance().getTime());
        storico.setQuantity(quantity);
        storicoRepository.save(storico);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Cart aupdateProduc(List<Product> productList, String email) throws UserNotFoundException, PriceChangedException, QuantityNotAvaibleException {
        Users user = usersRepository.findByEmail(email);
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
            if(product.getQuantity()==0)// se la quantità è a zero vado al prossimo prodotto
                break;
            Product product1 = productRepository.findProductByBarCode(product.getBarCode());
            if (!productInCartRepository.existsById(product1.getID())) {
                ProductInCart productInCart = new ProductInCart();
                productInCart.setProduct(product1);
                productInCart.setCart(c);
                if(product.getQuantity()<0)
                    throw new RuntimeException("Negative quantity");
                productInCart.setQuantity(product.getQuantity());
                listp.add(productInCart);
                quantityAvainlecheck(product);
                productInCartRepository.save(productInCart);
            } else {
                ProductInCart productInC2 = productInCartRepository.findByProductID(product1.getID());
                if(productInC2.getQuantity()+product.getQuantity()<0)// controllo nel caso si passi una quantità negativa che lo porti a zero
                    throw new RuntimeException("Impossibile operation, could set quantity:"+productInC2.getQuantity()+product.getQuantity());
                else if(productInC2.getQuantity()+product.getQuantity()==0) {
                    productInCartRepository.delete(productInC2);
                }else {

                    productInC2.setQuantity(productInC2.getQuantity() + product.getQuantity());
                    quantityAvainlecheck(product);
                }
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
        if (pro.getQuantity()<0 || p.getQuantity() - pro.getQuantity() < 0 ) {
            throw new QuantityNotAvaibleException();
        } else {// setta la quantità disponibile aggiornandola
            p.setQuantity(p.getQuantity() - pro.getQuantity());
        }
    }

    @Transactional(readOnly = true, propagation= Propagation.REQUIRED)
    public List<ProductInCart> getProductbyUser(String email) throws QuantityNotAvaibleException {
        System.out.println(email);
        Users u = usersRepository.findByEmail(email);
        System.out.println(u );
        Cart cart = cartRepository.findByUserID(u.getID());
        List<ProductInCart> PC=productInCartRepository.findAllByCartID(cart.getID());
        for(ProductInCart productInCart: PC ){
            if (productInCart.getProduct().getQuantity()-productInCart.getQuantity()<0)
                throw new QuantityNotAvaibleException("Quantity :"+ productInCart.getProduct().getQuantity());
        }
        return PC;
    }

    @Transactional(readOnly = false, propagation= Propagation.REQUIRED)
    public boolean deleteProd(String email,String barCod) throws Exception {
        Users u = usersRepository.findByEmail(email);
        Cart cart = cartRepository.findByUserID(u.getID());
        List<ProductInCart> PC=productInCartRepository.findAllByCartID(cart.getID());
        for(ProductInCart productInCart: PC ){
            if(productInCart.getProduct().getBarCode().equals(barCod)){
                productInCartRepository.delete(productInCart);
                cart.getListProductInCart().remove(productInCart);
                return true;
            }
        }
        return false;
    }
}