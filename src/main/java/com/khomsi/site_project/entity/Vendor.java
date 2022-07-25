package com.khomsi.site_project.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vendor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "vendor")
    @ToString.Exclude
    private List<Product> products;
}
