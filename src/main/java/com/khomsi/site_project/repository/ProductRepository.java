package com.khomsi.site_project.repository;

import com.khomsi.site_project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
   // List<Product> findAllByCategoryId(long categoryId);
}
