package com.example.progpi.controllers;

import com.example.progpi.Utilities.Exception.PriceChangedException;
import com.example.progpi.Utilities.Exception.QuantityNotAvaibleException;
import com.example.progpi.Utilities.Exception.UserNotFoundException;
import com.example.progpi.entities.Cart;
import com.example.progpi.entities.Product;

import com.example.progpi.entities.ProductInCart;
import com.example.progpi.repositories.ProductInCartRepository;
import com.example.progpi.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductInCartRepository productInCartRepository;


    @GetMapping("/getAllProd")
    public List<ProductInCart> getAllProducts(@RequestParam String codF) throws QuantityNotAvaibleException {
        return cartService.getProductbyUser(codF);
    }

    @GetMapping("/chekOut")
    public ResponseEntity<Boolean> chekOut(@RequestBody List<Product> productList,  @RequestParam("cF") String cF) throws UserNotFoundException,PriceChangedException,QuantityNotAvaibleException  {
       return new ResponseEntity(cartService.chekOut(productList, cF), HttpStatus.OK);
    }

    @GetMapping("/updateProduct")
    public ResponseEntity<Cart> addProduct(@RequestBody List<Product> productList,  @RequestParam("cF") String cF) throws UserNotFoundException,PriceChangedException,QuantityNotAvaibleException  {
        return new ResponseEntity(cartService.aupdateProduc(productList, cF), HttpStatus.OK);
    }


}
