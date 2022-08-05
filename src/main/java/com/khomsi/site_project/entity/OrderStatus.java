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
@Table(name = "order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;


    @Enumerated(EnumType.STRING)
    @Column(name = "status_name")
    private OrderType orderType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderStatus")
    @ToString.Exclude
    private List<Order> order;

}