package com.example.progpi.services;

import com.example.progpi.Utilities.Exception.ExistingUserException;
import com.example.progpi.entities.Cart;

import com.example.progpi.entities.Users;
import com.example.progpi.repositories.CartRepository;
import com.example.progpi.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;




@Service
public class UserService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CartRepository cartRepository;


    @Transactional(readOnly = false, propagation= Propagation.REQUIRED)
    public Users SaveUser(Users u) throws Exception{
        if(usersRepository.existsById(u.getID())){
            throw new ExistingUserException();
        }else {
            Cart cart = new Cart();
            u.setCart(cart);
            cart.setUser(u);
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
}