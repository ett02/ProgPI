package com.example.progpi.controllers;


import com.example.progpi.Utilities.Exception.QuantityNotAvaibleException;
import com.example.progpi.entities.ProductInCart;
import com.example.progpi.entities.Storico;
import com.example.progpi.repositories.StoricoRepository;
import com.example.progpi.services.StoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.progpi.Security.Authentication.Utils.getEmail;

@RestController
@RequestMapping("/storico")
public class StoricoController {

    @Autowired
    StoricoService storicoService;

    @GetMapping("/getStorico")
    public ResponseEntity<Page<Storico>> getAllProducts(@RequestParam("nPage") int nPage, @RequestParam("dPage") int dPage) throws QuantityNotAvaibleException {
        return new ResponseEntity( storicoService.getStorico(getEmail(),nPage,dPage), HttpStatus.OK);
    }


}
