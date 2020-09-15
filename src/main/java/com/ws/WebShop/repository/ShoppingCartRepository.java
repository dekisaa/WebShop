package com.ws.WebShop.repository;

import com.ws.WebShop.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository  extends JpaRepository<ShoppingCart, Long> {
}
