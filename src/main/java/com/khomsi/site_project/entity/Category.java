package com.khomsi.site_project.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
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
    @OrderBy("title asc")
    @ToString.Exclude
    private Set<Category> children = new HashSet<>();

    @OneToMany(mappedBy = "category", cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Product> products;

    @Column(name = "all_parent_ids", length = 255, nullable = true)
    @ToString.Exclude
    private String allParentsIDs;

    public static Category copyIdAndTitle(Category category) {
        Category copyCategory = new Category();
        copyCategory.setId(category.getId());
        copyCategory.setTitle(category.getTitle());

        return copyCategory;
    }

    public static Category copyIdAndTitle(Integer id, String title) {
        Category copyCategory = new Category();
        copyCategory.setId(id);
        copyCategory.setTitle(title);

        return copyCategory;
    }

    public static Category copyFull(Category category) {
        Category copyCategory = new Category();
        copyCategory.setId(category.getId());
        copyCategory.setTitle(category.getTitle());
        copyCategory.setAlias(category.getAlias());
        copyCategory.setImageURL(category.getImageURL());
        copyCategory.setEnabled(category.getEnabled());

        return copyCategory;
    }
    public static Category copyFull(Category category, String title) {
        Category copyCategory = Category.copyFull(category);
        copyCategory.setTitle(title);
        return copyCategory;
    }

    public Category(Integer id) {
        this.id = id;
    }

    public Category(String title, Category parent) {
        this(title);
        this.parent = parent;
    }

    public Category(String title) {
        this.title = title;
        this.alias = title;
        this.imageURL = "default.png";
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