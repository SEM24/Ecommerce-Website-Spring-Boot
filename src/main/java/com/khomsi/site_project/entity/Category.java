package com.khomsi.site_project.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@AllArgsConstructor
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

    @Column(name = "alias")
    private String alias;

    @Column(name = "image")
    private String imageURL;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    //parent_id refers to category id or null if it's top-level category
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    @ToString.Exclude
    private Set<Category> children = new HashSet<>();

    @OneToMany(mappedBy = "category", cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Product> products;

//    public Category(String title) {
//        this.title = title;
//    }
    //FIXME проверить все конструкторы
    public Category(String title) {
        this.title = title;
        this.alias = title;
        this.imageURL = "default.png";
    }

    public Category(String title, Category parent) {
        this(title);
        this.parent = parent;
    }

    public Category(Integer id) {
        this.id = id;
    }

    public Category(Integer id, String title, String alias, String imageURL, Boolean enabled, Category parent) {
        this.id = id;
        this.title = title;
        this.alias = alias;
        this.imageURL = imageURL;
        this.enabled = enabled;
        this.parent = parent;
    }
}