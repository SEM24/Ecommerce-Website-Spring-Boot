package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.exception.ProductNotFoundException;

import java.util.List;

public interface IProductService {
    public List<Product> getAllProducts();

    public void saveProduct(Product product);

    Product getProduct(Integer id) throws ProductNotFoundException;
    Product getProduct(String alias) throws ProductNotFoundException;

//    public void deleteProduct(int id);

    void deleteProduct(Integer id) throws ProductNotFoundException;

}
