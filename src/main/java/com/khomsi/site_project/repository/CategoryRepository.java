package com.khomsi.site_project.repository;

import com.khomsi.site_project.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT category FROM Category category WHERE category.enabled = true ORDER BY category.title ASC")
    public List<Category> findAllEnabled();

    @Query("select category from Category category where category.enabled = true and category.alias = ?1")
    public Category findByAliasEnabled(String alias);
}
