package com.example.progpi.controllers;

import com.example.progpi.Utilities.Exception.NotExistingUserException;
import com.example.progpi.entities.Users;
import com.example.progpi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.progpi.Security.Authentication.Utils.getEmail;

@RestController

@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService uS;

    @PutMapping("/add")
    public Users saveUser(@RequestBody Users u){
        try {
            return uS.saveUser(u);
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("/getUsers")
    public Users getUser(){
        return uS.getUser(getEmail());
    }

    @GetMapping("/esiste")
    public boolean Esiste(@RequestParam("email")String email){
        return uS.Esiste(email);
    }

    @GetMapping("/delette")
    public boolean Delete(@RequestParam("codF")String codFisc) throws NotExistingUserException {
            return uS.delette(codFisc);
    }

}
