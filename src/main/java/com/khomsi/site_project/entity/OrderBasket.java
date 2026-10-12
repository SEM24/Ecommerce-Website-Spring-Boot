package com.khomsi.site_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Transient;

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
