package com.example.progpi.services;

import com.example.progpi.Utilities.Exception.ExistingUserException;
import com.example.progpi.Utilities.Exception.NotExistingUserException;
import com.example.progpi.entities.Cart;
import com.example.progpi.entities.Users;
import com.example.progpi.repositories.CartRepository;
import com.example.progpi.repositories.ProductInCartRepository;
import com.example.progpi.repositories.UsersRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    private ProductInCartRepository productInCartRepository;

    EntityManager entityManager;


    @Transactional(readOnly = false, propagation= Propagation.REQUIRED)
    public Users saveUser(Users u) throws Exception{
        if(usersRepository.existsByEmail(u.getEmail())){
            throw new ExistingUserException();
        }else {
            Cart cart = new Cart();
            u.setCart(cart);
            cart.setUser(u);
            cart.setListProductInCart(new ArrayList<>());
            cartRepository.save(cart);
            usersRepository.save(u);
            return u;
        }
    }

    @Transactional(readOnly = true, propagation= Propagation.REQUIRED)
    public Users getUser(String email) {
        return usersRepository.findByEmail(email);
    }

    @Transactional(readOnly = true, propagation= Propagation.REQUIRED)
    public boolean Esiste(String email){
        return usersRepository.existsByEmail(email);
    }

    /*
    @Transactional(readOnly = false, propagation= Propagation.REQUIRED)
    public Users updateUser(Users u, String cF) throws Exception{
        Users user = usersRepository.findByCodFisc(cF);

        user.setEmail(u.getEmail());
        user.setName(u.getName());
        user.setSurname(u.getSurname());
        user.setAddress(u.getAddress());
        user.setTelephon(u.getTelephon());



    }

     */

    @Transactional(readOnly = false)
    public boolean delette(String cF) throws NotExistingUserException {

        if(!usersRepository.existsByCodFisc(cF)){
            throw new NotExistingUserException();
        }
        Users u = usersRepository.findByCodFisc(cF);

        usersRepository.delete(u);
        return !usersRepository.existsByCodFisc(cF);
    }

}