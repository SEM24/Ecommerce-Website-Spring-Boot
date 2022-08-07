package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.exception.ProductNotFoundException;

import java.util.List;

public interface IProductService {
    public List<Product> getAllProducts();

    public void saveProduct(Product product);
//    //TODO реализовать поиск по тайтлу(алиасу)
//    //    public Product getProduct(String title) throws ProductNotFoundException;
//    public Product getProduct(int id);

    Product getProduct(Integer id) throws ProductNotFoundException;

    public void deleteProduct(int id);
}
