package com.example.progpi.controllers;

import com.example.progpi.entities.Users;
import com.example.progpi.repositories.UsersRepository;
import com.example.progpi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    public Users SaveUser(@RequestBody Users u){
        try {
            return uS.SaveUser(u);
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("/getUsers")
    public Users getUser(@RequestParam("email")String email){
        return uS.getUser(email);
    }

}
