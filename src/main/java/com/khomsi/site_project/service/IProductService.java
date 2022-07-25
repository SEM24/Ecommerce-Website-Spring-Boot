package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Product;

import java.util.List;

public interface IProductService {
    public List<Product> getAllProducts();

    public void saveProduct(Product product);

    public Product getProduct(int id);

    public void deleteProduct(int id);
}
