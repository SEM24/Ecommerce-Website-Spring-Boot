package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "order_status")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class OrderStatus {
    //TODO Нужен тест для этого класса

    @Id
    @Column(name = "id")
    private int id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private EOrderStatus orderStatus;
}
