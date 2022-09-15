package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {

    Page<Product> listByPage(int pageNum, String sortField, String sortDir, String keyword,
                             Integer categoryId);

    public List<Product> getAllProducts() throws ProductNotFoundException;

    List<Product> getRandomAmountOfProducts() throws ProductNotFoundException;

    public Page<Product> listByCategory(int pageNum, Integer categoryId);

    public void saveProduct(Product product);

    Product getProduct(Integer id) throws ProductNotFoundException;

    Product getProduct(String alias) throws ProductNotFoundException;

    void deleteProduct(Integer id) throws ProductNotFoundException;


    String checkUnique(Integer id, String title);
}
