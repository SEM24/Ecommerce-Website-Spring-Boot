package com.khomsi.site_project.entity;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderType orderStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "shipping_type")
    private int shippingType;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "total_price")
    private Float totalPrice;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Delivery delivery;

    public Order() {
    }

    public Order(int id, OrderType orderStatus, User user, int shippingType, String city, String address, Float totalPrice, Delivery delivery) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.user = user;
        this.shippingType = shippingType;
        this.city = city;
        this.address = address;
        this.totalPrice = totalPrice;
        this.delivery = delivery;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderType getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderType orderStatus) {
        this.orderStatus = orderStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getShippingType() {
        return shippingType;
    }

    public void setShippingType(int shippingType) {
        this.shippingType = shippingType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}