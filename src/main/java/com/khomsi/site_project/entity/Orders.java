package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
//source table
public class Orders {
    //TODO Нужен тест для этого класса

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "price")
    private int price;

    //FIXME тут проверить на всякий, @ManyToOne should annotate a field not a collection.
    // For collection fields the right annotation is @OneToMany.

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User userId;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatusId;

    @NonNull
    @Column(name = "shippingType")
    private int shippingType;

}
