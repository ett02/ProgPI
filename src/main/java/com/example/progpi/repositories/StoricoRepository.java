package com.example.progpi.repositories;

import com.example.progpi.entities.Product;
import com.example.progpi.entities.Storico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoricoRepository extends JpaRepository<Storico, Integer> {

    @Query("SELECT p FROM Storico p WHERE p.user.ID = :id")
    Page<Storico> findAllByUserID(@Param("id") int id, Pageable pageable);


}
