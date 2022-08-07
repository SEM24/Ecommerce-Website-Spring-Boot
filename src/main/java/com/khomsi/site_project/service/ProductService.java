package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.exception.ProductNotFoundException;
import com.khomsi.site_project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
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
    //TODO реализовать поиск по тайтлу(алиасу)
//    @Override
//    public Product getProduct(String title) throws ProductNotFoundException {
//        Product product = productRepository.findByCategory(title);
//        if (product == null) {
//            throw new ProductNotFoundException("Couldn't find any product with title " + title);
//        }
//        return product;
//    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
