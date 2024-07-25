package com.example.progpi.repositories;

import com.example.progpi.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>{

    Users findByEmail(String email);


    boolean existsByEmail(String email);

}
