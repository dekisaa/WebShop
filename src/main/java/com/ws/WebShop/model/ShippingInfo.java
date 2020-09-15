package com.ws.WebShop.model;

import javax.persistence.*;

@Entity
public class ShippingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column
    private ShippingType shippingType;

    @Column
    private Double shippingCost;

    @OneToOne(mappedBy = "shippingInfo", cascade = CascadeType.ALL)
    private UsersOrder usersOrder;

    public ShippingInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsersOrder getUsersOrder() {
        return usersOrder;
    }

    public void setUsersOrder(UsersOrder usersOrder) {
        this.usersOrder = usersOrder;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public ShippingType getShippingType() {
        return shippingType;
    }

    public void setShippingType(ShippingType shippingType) {
        this.shippingType = shippingType;
    }
}
