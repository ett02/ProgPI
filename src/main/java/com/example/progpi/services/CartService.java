package com.example.progpi.services;

import com.example.progpi.Utilities.Exception.*;
import com.example.progpi.entities.*;
import com.example.progpi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<ProductInCart> chekOut(List<ProductInCart> productList, String email) throws UserNotFoundException, PriceChangedException, QuantityNotAvaibleException {
        Users user = usersRepository.findByEmail(email);

        if (!usersRepository.existsById(user.getID())) {
            throw new UserNotFoundException();
        }
        Cart c = cartRepository.findCartByUserID(user.getID());
        List<ProductInCart> listp = c.getListProductInCart();

        for (ProductInCart product : productList) {

            if (!productRepository.existsProductByBarCode(product.getProduct().getBarCode())) {
                throw new RuntimeException("prod non esiste");
            }
            if (product.getProduct().getPrice() != productRepository.findProductByBarCode(product.getProduct().getBarCode()).getPrice()) { // se il prezzo è cambiato
                throw new PriceChangedException();// da fare
            }

            Product p = productRepository.findProductByBarCode(product.getProduct().getBarCode());
            if (productInCartRepository.existsByProductID(p.getID())) {//see è nel carrello
                chek(product.getProduct());
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


    @Transactional(readOnly=false,rollbackFor = Exception.class)public Cart updateProduct(List<Product> products, String email) throws UserNotFoundException,PriceChangedException, QuantityNotAvaibleException {
        Users user = usersRepository.findByEmail(email);

        if (!usersRepository.existsById(user.getID()))
            throw new UserNotFoundException();
        Cart cart = cartRepository.findByUserID(user.getID());
        List<ProductInCart> productInCarts = cart.getListProductInCart();
        for (Product product : products) {

            if (!productRepository.existsProductByBarCode(product.getBarCode())) {
                throw new RuntimeException();
            }
            if (product.getPrice() != productRepository.findProductByBarCode(product.getBarCode()).getPrice()) {
                throw new PriceChangedException();// da fare
                }
                if (product.getQuantity() == 0)//se passo un prodotto con quantità a zero passo avanti
                    break;
                Product p = productRepository.findProductByBarCode(product.getBarCode());
                if (!productInCartRepository.existsByProductID(p.getID())) {//se non è presente nel carrello

                    ProductInCart productInCart = new ProductInCart();
                    productInCart.setProduct(p);
                    productInCart.setCart(cart);
                    if (product.getQuantity() < 0)
                        throw new QuantityNotAvaibleException("negativeQuantity");
                    productInCart.setQuantity(product.getQuantity());
                    productInCarts.add(productInCart);
                    int tmp = productInCart.getQuantity() + product.getQuantity();
                    Product n = productRepository.findProductByBarCode(product.getBarCode());
                    if (n.getQuantity() - tmp < 0)
                        throw new RuntimeException();
                    productInCartRepository.save(productInCart);

                } else {
                    ProductInCart productInCart = productInCartRepository.findByProductID(p.getID()); //se è già presente nel carrello
                    if(productInCart.getQuantity() + product.getQuantity()<0)
                    throw new QuantityNotAvaibleException("negativeQuantity:" + (productInCart.getQuantity() + product.getQuantity()));

                    else if (productInCart.getQuantity() + product.getQuantity() == 0) {
                        productInCartRepository.delete(productInCart);
                    } else {
                        int tmp = productInCart.getQuantity() + product.getQuantity();
                        Product n = productRepository.findProductByBarCode(product.getBarCode());
                        if (n.getQuantity() - tmp < 0)
                            throw new QuantityNotAvaibleException("quantità no ndisponibile");
                        productInCart.setQuantity(productInCart.getQuantity() + product.getQuantity());
                        //aux(product);
                        }
                    }
                }
                return cart;
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
    public Page<ProductInCart> getProductbyUser(String email,int nPage,int dPage) throws QuantityNotAvaibleException {
        Pageable pageRequest= PageRequest.of(nPage,dPage);

        Users u = usersRepository.findByEmail(email);
        System.out.println(u );
        Cart cart = cartRepository.findByUserID(u.getID());
        Page<ProductInCart> PC=productInCartRepository.findAllByCartID(cart.getID(),pageRequest);
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
        List<ProductInCart> PC=productInCartRepository.findAllProductByCartID(cart.getID());
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