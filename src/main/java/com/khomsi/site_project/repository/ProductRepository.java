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

    @Query(value = "SELECT * FROM product WHERE MATCH(title, description) AGAINST (?1)",
            nativeQuery = true)
    public Page<Product> search(String keyword, Pageable pageable);

    public Product findByAlias(String alias);

    public Product findByTitle(String title);

    public Long countById(Integer id);
}
