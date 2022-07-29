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
    @OneToOne
    @MapsId
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeliveryStatus status;



}
