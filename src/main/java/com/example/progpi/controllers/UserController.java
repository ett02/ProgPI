package com.example.progpi.controllers;

import com.example.progpi.Utilities.Exception.NotExistingUserException;
import com.example.progpi.entities.Users;
import com.example.progpi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.progpi.Security.Authentication.Utils.getEmail;

@RestController

@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService uS;

    @PutMapping("/add")
    public ResponseEntity<Users> saveUser(@RequestBody Users u) throws Exception{
        return new ResponseEntity( uS.saveUser(u),HttpStatus.OK);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<Users>  getUser(){
        return new ResponseEntity(  uS.getUser(getEmail()), HttpStatus.OK);
    }

    @GetMapping("/delette")
    public ResponseEntity<Boolean> Delete(@RequestParam("codF")String codFisc) throws NotExistingUserException {
            return new ResponseEntity( uS.delette(codFisc), HttpStatus.OK);
    }



}
