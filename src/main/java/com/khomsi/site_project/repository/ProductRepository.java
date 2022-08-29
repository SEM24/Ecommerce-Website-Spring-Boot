package com.khomsi.site_project.repository;

import com.khomsi.site_project.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT product FROM Product product WHERE (product.category.id = ?1 OR product.category.allParentsIDs LIKE %?2%)"
            + "ORDER BY product.title ASC")
    public Page<Product> listByCategory(Integer categoryId, Pageable pageable, String categoryIDMatch);

    public Product findByAlias(String alias);

    //TODO make test
    public Long countById(Integer id);
}
