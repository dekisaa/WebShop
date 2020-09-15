package com.ws.WebShop.controller;

import com.ws.WebShop.model.CartItem;
import com.ws.WebShop.model.ShoppingCart;
import com.ws.WebShop.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shopping_cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping(value= "/clear", method = RequestMethod.POST)
    public ResponseEntity<?> clear(){
        shoppingCartService.clear();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value= "/submit", method = RequestMethod.POST)
    public ResponseEntity<?> submit(@RequestParam(value = "free") Boolean free){
        shoppingCartService.submit(free);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartItem>> getShoppingCart(){
        return new ResponseEntity<>(shoppingCartService.getContentOfShoppingCart(), HttpStatus.OK);
    }
}
