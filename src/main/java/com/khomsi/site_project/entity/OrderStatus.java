package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order_status")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderStatus {

    @Id
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_name")
    private OrderType orderType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderStatusId")
    @ToString.Exclude
    private List<Orders> ordersListStatus;


    //
//    //Этот пользователь будет пользователем данного заказа
//    public void addOrderToStatus(Orders orders) {
//        if (ordersListStatus == null) ordersListStatus = new ArrayList<>();
//
//        ordersListStatus.add(orders);
//        orders.setOrderStatusId(this);
//    }
}
