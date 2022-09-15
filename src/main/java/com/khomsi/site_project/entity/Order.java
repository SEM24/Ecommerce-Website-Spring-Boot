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

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderType orderStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
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
    @ToString.Exclude
    private Delivery delivery;


}