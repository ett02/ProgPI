package com.example.progpi.controllers;

import com.example.progpi.entities.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController

@RequestMapping("/users")
public class UserController {
    @GetMapping("/test")
    public String test(){

        return "Hello";
    }

    @GetMapping("/add")
    public String SaveUser(@RequestBody User u){
        if(u.getEmail()!=null){
            return "utente con email: "+u.getEmail();
        }
        return "utente: "+u.getName();
    }

}
