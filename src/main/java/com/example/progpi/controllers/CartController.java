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

import static com.example.progpi.Security.Authentication.Utils.getEmail;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductInCartRepository productInCartRepository;

    //@RequestParam String codF
    @GetMapping("/getAllProd")
    public ResponseEntity<List<ProductInCart>> getAllProducts() throws QuantityNotAvaibleException {
        return new ResponseEntity( cartService.getProductbyUser(getEmail()), HttpStatus.OK);
    }

    @PutMapping("/chekOut")
    public ResponseEntity<Product> chekOut(@RequestBody List<Product> productList) throws UserNotFoundException,PriceChangedException,QuantityNotAvaibleException  {
       return new ResponseEntity(cartService.chekOut(productList, getEmail()), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> addProduct(@RequestBody List<Product> productList) throws UserNotFoundException,PriceChangedException,QuantityNotAvaibleException  {
        return new ResponseEntity(cartService.aupdateProduc(productList, getEmail()), HttpStatus.OK);
    }

    @DeleteMapping("/deletteProd")
    public ResponseEntity<Boolean> deletteProd(@RequestParam("barCode") String barCode) throws Exception {
        return new ResponseEntity(cartService.deleteProd(getEmail(),barCode), HttpStatus.OK);
    }

}
