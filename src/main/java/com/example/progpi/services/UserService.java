package com.example.progpi.services;

import com.example.progpi.entities.Users;
import com.example.progpi.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserService {

    @Autowired
    UsersRepository usersRepository;

    public Users SaveUser(Users u) {
        return usersRepository.save(u);
    }

    public Users getUser(String email) {
        return usersRepository.findByEmail(email);
    }

    public boolean Esiste(String email){
        return usersRepository.existsByEmail(email);
    }
}