package com.ws.WebShop.controller;

import com.ws.WebShop.model.CartItem;
import com.ws.WebShop.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cart_item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @RequestMapping( method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestParam(value = "id") Long id){
        cartItemService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody CartItem cartItem){
        cartItemService.update(cartItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartItem> findById(@RequestParam(value = "id") Long id){
        return new ResponseEntity<>(cartItemService.findById(id), HttpStatus.OK);
    }

    @RequestMapping( value = "/add_to_cart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addToCart(@RequestParam(value = "productId") Long productId, @RequestParam(value = "quantity") Integer quantity){
        cartItemService.addToCart(productId,quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/remove_from_cart", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeToCart(@RequestParam(value = "id") Long id){
        cartItemService.removeFromCart(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
