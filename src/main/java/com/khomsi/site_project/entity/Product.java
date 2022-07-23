package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    @Id
    @Column(name = "id")
 //   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // @NotNull
    @Column(name = "title")
    private String title;

    //@NotNull
    @Column(name = "description")
    private String description;

    //@NotNull
    @Column(name = "price")
    private int price;

    //TODO нужно ли добавлять  @NonNull сюда?

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vendor_id")
    private Vendor vendorId;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category categoryId;
}
