package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    //    @ManyToOne(cascade = {CascadeType.PERSIST,
//            CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    //    @ManyToOne(cascade = {CascadeType.PERSIST,
//            CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "order_basket",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "orders_id"))
    @ToString.Exclude
    private List<Orders> ordersList;

    @Column(name = "image")
    private String imageURL;

}
