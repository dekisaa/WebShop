package com.ws.WebShop.model;

import javax.persistence.*;

@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column
    private Integer quantity;

    @Column
    private Double unitCost;

    @Column
    private Double subtotal;

    @ManyToOne
    private UsersOrder usersOrder;

    @OneToOne(mappedBy = "orderDetail")
    private Product product;

    public OrderDetail() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UsersOrder getUsersOrder() {
        return usersOrder;
    }

    public void setUsersOrder(UsersOrder usersOrder) {
        this.usersOrder = usersOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

}
