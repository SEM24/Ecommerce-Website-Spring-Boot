package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "delivery")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Delivery {
    @Id
    @Column(name = "orders_id")
    private int orders_id;

    @Column(name = "shippingType")
    private int shippingType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeliveryStatus status;

}
