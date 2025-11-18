package com.khomsi.site_project.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vendor")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "vendor")
    private List<Product> products;

    public Vendor() {
    }

    public Vendor(Integer id, String title, List<Product> products) {
        this.id = id;
        this.title = title;
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}