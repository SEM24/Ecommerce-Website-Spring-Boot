package com.khomsi.site_project.repository;

import com.khomsi.site_project.entity.Category;
import com.khomsi.site_project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByCategory(String category);
}
