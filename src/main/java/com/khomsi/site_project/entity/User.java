package com.khomsi.site_project.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//target table
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserDetails userDetails;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "qty")
    private int qty;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    //FIXME
    //У одного пользователя может быть много заказов
    // @ToString.Exclude
    @OneToMany(mappedBy = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<Orders> ordersListUser;

    //Если заказа нету, создать его, если есть, добавить его в лист
//    public void addOrderToUser(Orders orders) {
//        if (ordersListUser == null) ordersListUser = new ArrayList<>();
//
//        ordersListUser.add(orders);
//        orders.setUserId(this);
//    }

}
