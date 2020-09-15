package com.ws.WebShop.service;

import com.ws.WebShop.model.*;
import com.ws.WebShop.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private UsersOrderService usersOrderService;

    @Autowired
    private ShippingInfoService shippingInfoService;

    public void save(ShoppingCart shoppingCart){
        shoppingCartRepository.save(shoppingCart);
    }

    public void clear(){
        ShoppingCart shoppingCart = getShoppingCart();
        for(CartItem ci : shoppingCart.getCartItems()){
            ci.setShoppingCart(null);
            cartItemService.save(ci);
        }
        shoppingCart.getCartItems().clear();
        save(shoppingCart);
    }

    public void submit(Boolean free) {
        ShoppingCart shoppingCart = getShoppingCart();

        if(shoppingCart.getCartItems().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty cart!");

        UsersOrder usersOrder = new UsersOrder();
        usersOrder.setDateCreated(new Date());
        usersOrder.setStatus("ACCEPTED");
        //TODO postavi usera
        //usersOrder.setUser();
        for(CartItem ci : shoppingCart.getCartItems()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(ci.getQuantity());
            orderDetail.setSubtotal(ci.getSubtotal());
            orderDetail.setUnitCost(ci.getProduct().getPrice());
            orderDetail.setProduct(ci.getProduct());
            usersOrder.getOrderDetails().add(orderDetail);
            orderDetail.setUsersOrder(usersOrder);
        }

        ShippingInfo shippingInfo = new ShippingInfo();
        if(free)
            shippingInfo.setShippingType(ShippingType.FREE);
        else
            shippingInfo.setShippingType(ShippingType.PAYED);

        shippingInfo.setShippingCost(getShippingCost());
        shippingInfo.setUsersOrder(usersOrder);

        shippingInfoService.save(shippingInfo);

        usersOrder.setShippingInfo(shippingInfo);
        usersOrderService.save(usersOrder);

        clear();
    }

    public Double getShippingCost(){
        ShoppingCart shoppingCart = getShoppingCart();
        Double sum = 0d;
        for(CartItem ci : shoppingCart.getCartItems())
            sum += ci.getProduct().getShippingPrice();
        return sum;
    }

    public List<CartItem> getContentOfShoppingCart(){
        ShoppingCart shoppingCart = getShoppingCart();
        return shoppingCart.getCartItems();
    }

    public ShoppingCart getShoppingCart(){
        //TODO prepraviti da uzima od ulogovanog usera shopping cart
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById((long) 1);
        if(!shoppingCart.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem with shopping cart!");
        return shoppingCart.get();
    }
}
