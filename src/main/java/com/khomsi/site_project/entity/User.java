package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
//target table
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "phone")
    @NonNull
    private String phone;

    @Column(name = "email")
    @NonNull
    private String email;

    @Column(name = "city")
    @NonNull
    private String city;

    @Column(name = "qty")
    @NonNull
    private int qty;

    //TODO я хз еще надо ли тут  @ToString.Exclude, типо нужна ли эта
    //строка тут

    //У одного пользователя может быть много заказов
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Orders> ordersListUser;

    //Этот пользователь будет пользователем данного заказа
    public void addOrderToUser(Orders orders) {
        if (ordersListUser == null) ordersListUser = new ArrayList<>();

        ordersListUser.add(orders);
        orders.setUserId(this);
    }
}
