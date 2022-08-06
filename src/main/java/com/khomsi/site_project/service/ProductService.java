package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.exception.ProductNotFoundException;
import com.khomsi.site_project.repository.ProductRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Product getProduct(int id) {
        Product product = null;
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            product = optional.get();
        }
        return product;
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
