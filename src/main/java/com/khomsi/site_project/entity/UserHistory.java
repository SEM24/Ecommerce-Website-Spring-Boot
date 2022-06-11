package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_history")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class UserHistory {
    //TODO тест нужен этого класса

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

    //У одного пользователя может быть много заказов
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userHistoryId")
    private List<OrderHistory> orderHistoryList;

    //Этот пользователь будет пользователем данного заказа
    public void addOrderToUser(OrderHistory orderHistory) {
        if (orderHistoryList == null) orderHistoryList = new ArrayList<>();

        orderHistoryList.add(orderHistory);
        orderHistory.setUserHistoryId(this);
    }
}
