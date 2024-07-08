package com.example.progpi.controllers;

import com.example.progpi.entities.User;
import com.example.progpi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController

@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService uS;

    @GetMapping("/test")
    public String test(){

        return "Hello";
    }

    @GetMapping("/add")
    public String SaveUser(@RequestBody User u){
        uS.SaveUser(u);
        return "utente salvato: "+u.toString();
    }

}
