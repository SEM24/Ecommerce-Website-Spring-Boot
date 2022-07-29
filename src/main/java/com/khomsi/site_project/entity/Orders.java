package com.khomsi.site_project.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
//source table
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "order_basket",
            joinColumns = @JoinColumn(name = "orders_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @ToString.Exclude
    private List<Product> productList;


    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Delivery delivery;

    // @ManyToOne should annotate a field not a collection.
    // For collection fields the right annotation is @OneToMany.
//FIXME
    @ToString.Exclude
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User userId;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatusId;

    @Column(name = "shipping_type")
    private int shippingType;
}
