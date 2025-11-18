package com.khomsi.site_project.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "alias")
    private String alias;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "image")
    private String imageURL;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderBasket> orderBaskets;

    public Product() {
    }

    public Product(Integer id, String title, String alias, String description, int price, String imageURL, Vendor vendor, Category category, List<OrderBasket> orderBaskets) {
        this.id = id;
        this.title = title;
        this.alias = alias;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
        this.vendor = vendor;
        this.category = category;
        this.orderBaskets = orderBaskets;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<OrderBasket> getOrderBaskets() {
        return orderBaskets;
    }

    public void setOrderBaskets(List<OrderBasket> orderBaskets) {
        this.orderBaskets = orderBaskets;
    }

    @Transient
    public String getShortTitle() {
        if (title.length() > 40) {
            return title.substring(0, 40).concat("...");
        }
        return title;
    }
    @Transient
    public String getShortAlias() {
        if (alias.length() > 40) {
            return alias.substring(0, 40).concat("...");
        }
        return alias;
    }

    @Transient
    public String getShortDescription() {

        if (description != null && description.length() > 50) {
            return description.substring(0, 50).concat("...");
        }
        return description;
    }


    //TODO add a new field in db discount if i need discount in future
//    @Transient
//    public float getDiscountPrice(){
//        if (discountPercent > 0)
//        {
//            return price * ((100 - discountPercent) / 100);
//        }
//        return this.price;
//    }

}