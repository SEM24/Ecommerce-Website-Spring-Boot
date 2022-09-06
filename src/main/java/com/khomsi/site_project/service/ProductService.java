package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.exception.ProductNotFoundException;
import com.khomsi.site_project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    public static final int PRODUCTS_PER_PAGE = 10;

    public Page<Product> listByCategory(int pageNum, Integer categoryId) {
        String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);

        return productRepository.listByCategory(categoryId, pageable, categoryIdMatch);
    }


    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        List<Product> listProducts = productRepository.findAll();
        if (listProducts.isEmpty()) {
            throw new ProductNotFoundException("Couldn't find any product in DB");
        }
        return listProducts;
    }

    @Override
    public List<Product> getRandomAmountOfProducts() throws ProductNotFoundException {
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            throw new ProductNotFoundException("Couldn't find any product in DB");
        }
        Collections.shuffle(productList);
        int randomSeriesLength = 8;
        return productList.subList(0, randomSeriesLength);

    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product getProduct(Integer id) throws ProductNotFoundException {
        try {
            return productRepository.getReferenceById(id);
        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException("Couldn't find any product with id " + id);
        }
    }

    @Override
    public Product getProduct(String alias) throws ProductNotFoundException {
        try {
            return productRepository.findByAlias(alias);
        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException("Couldn't find any product with alias " + alias);
        }
    }

    @Override
    public void deleteProduct(Integer id) throws ProductNotFoundException {
        Long countById = productRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new ProductNotFoundException("Couldn't find any product with ID " + id);
        }
        productRepository.deleteById(id);
    }
}
