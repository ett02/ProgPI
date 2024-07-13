package com.example.progpi.repositories;

import com.example.progpi.entities.Storico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoricoRepository extends JpaRepository<Storico, Integer> {

    List<Storico> findAllByUserEmail(String email);


}
