package com.example.progpi.services;


import com.example.progpi.Utilities.Exception.QuantityNotAvaibleException;
import com.example.progpi.entities.Cart;
import com.example.progpi.entities.ProductInCart;
import com.example.progpi.entities.Storico;
import com.example.progpi.entities.Users;
import com.example.progpi.repositories.*;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class StoricoService {

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

    @Transactional(readOnly = true, propagation= Propagation.REQUIRED)
    public Page<Storico> getStorico(String email, int nPage, int dPage){
        Pageable pageRequest= PageRequest.of(nPage,dPage);
        Users u = usersRepository.findByEmail(email);
        return storicoRepository.findAllByUserID(u.getID(), pageRequest);
    }

}
