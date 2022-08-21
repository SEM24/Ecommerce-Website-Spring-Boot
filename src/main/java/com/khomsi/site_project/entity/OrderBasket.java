package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "order_basket")
public class OrderBasket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @Column(name = "quantity")
    private int quantity;

    //We use temporary field that is not in db for business logic(we don't need to save it in db)
    @Transient
    public float getSubtotal() {
        return this.product.getPrice() * quantity;
    }

//    @Transient
//    public float getTotal() {
//        float sum = 0;
//        for (OrderBasket orderBasket : this.product.getOrderBaskets()) {
//            sum += orderBasket.getSubtotal();
//        }
//        return sum;
//    }
}