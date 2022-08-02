package com.khomsi.site_project.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "delivery")
public class Delivery {
    @Id
    @Column(name = "orders_id")
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "orders_id")
    @ToString.Exclude
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeliveryStatus status;


}