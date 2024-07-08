package com.example.progpi.services;


import com.example.progpi.entities.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    public ArrayList<User> db = new ArrayList<>();

    public User SaveUser(User u) {
        db.add(u);
        return u;
    }

}
