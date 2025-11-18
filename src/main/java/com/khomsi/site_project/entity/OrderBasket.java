package com.khomsi.site_project.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_basket")
public class OrderBasket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "quantity")
    private int quantity;

    public OrderBasket() {
    }

    public OrderBasket(int id, Product product, User user, int quantity) {
        this.id = id;
        this.product = product;
        this.user = user;
        this.quantity = quantity;
    }

    //We use temporary field that is not in db for business logic(we don't need to save it in db)
    @Transient
    public float getSubtotal() {
        return this.product.getPrice() * quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}