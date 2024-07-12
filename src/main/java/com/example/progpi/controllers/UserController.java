package com.example.progpi.controllers;

import com.example.progpi.Utilities.Exception.NotExistingUserException;
import com.example.progpi.entities.Users;
import com.example.progpi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController

@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService uS;

    @GetMapping("/add")
    public Users saveUser(@RequestBody Users u){
        try {
            return uS.saveUser(u);
        }catch (Exception e){
            return null;
        }
    }
    @GetMapping("/getUsers")
    public Users getUser(@RequestParam("email")String email){
        return uS.getUser(email);
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
