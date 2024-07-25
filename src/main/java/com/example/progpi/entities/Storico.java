package com.example.progpi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "storico")
public class Storico implements Serializable {

    @Id
    @JsonIgnore
    @ToString.Exclude
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int ID;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "related_user")
    private Users user;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "related_product")
    private Product product;

    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Basic
    @Column(name = "time", length = 80, nullable = false)
    private Date time;



}
