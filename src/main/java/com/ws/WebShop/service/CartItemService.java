package com.ws.WebShop.service;

import com.ws.WebShop.model.CartItem;
import com.ws.WebShop.model.Product;
import com.ws.WebShop.model.ShoppingCart;
import com.ws.WebShop.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    public void save(CartItem cartItem){
        cartItemRepository.save(cartItem);
    }

    public void update(CartItem cartItem){
        CartItem origCartItem = findById(cartItem.getId());
        origCartItem.setQuantity(cartItem.getQuantity());
        origCartItem.setSubtotal(origCartItem.getProduct().getPrice()*cartItem.getQuantity());
        save(origCartItem);
    }

    public void delete(Long id){
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        if(!cartItem.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CartItem with id " + id + " does not exist!");
        cartItemRepository.delete(cartItem.get());
    }

    public void delete(CartItem cartItem){
        cartItemRepository.delete(cartItem);
    }

    public CartItem findById(Long id){
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        if(!cartItem.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CartItem with id " + id + " does not exist!");
        return cartItem.get();
    }

    public void addToCart(Long productId, Integer quantity){
        Product product = productService.findById(productId);
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(quantity);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setProduct(product);
        cartItem.setSubtotal(quantity*product.getPrice());
        cartItem.setShoppingCart(shoppingCart);
        save(cartItem);
        shoppingCart.getCartItems().add(cartItem);
        shoppingCartService.save(shoppingCart);
    }

    public void removeFromCart(Long id){
        CartItem cartItem = findById(id);

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
        shoppingCart.getCartItems().remove(cartItem);
        delete(cartItem);
        shoppingCartService.save(shoppingCart);
    }
}
