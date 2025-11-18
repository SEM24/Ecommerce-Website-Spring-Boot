package com.khomsi.site_project.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Category> children = new HashSet<>();

    @OneToMany(mappedBy = "category", cascade = {CascadeType.ALL, CascadeType.PERSIST})
    private List<Product> products;

    @Column(name = "all_parent_ids", length = 255, nullable = true)
    private String allParentsIDs;

    public Category() {
    }

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getAllParentsIDs() {
        return allParentsIDs;
    }

    public void setAllParentsIDs(String allParentsIDs) {
        this.allParentsIDs = allParentsIDs;
    }
}