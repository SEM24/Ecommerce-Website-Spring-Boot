package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "order_status_id")
    @ToString.Exclude
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_basket_id")
    @ToString.Exclude
    private OrderBasket orderBasket;

    @Column(name = "shipping_type")
    private int shippingType;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "total_price")
    private Float totalPrice;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Delivery delivery;

}