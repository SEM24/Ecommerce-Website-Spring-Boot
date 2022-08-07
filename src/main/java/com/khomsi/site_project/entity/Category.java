package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 155)
    private String title;

    @Column(name = "alias", nullable = false, length = 255)
    private String alias;

    @Column(name = "image")
    private String imageURL;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Product> products;

}