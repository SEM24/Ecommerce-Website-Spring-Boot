package com.khomsi.site_project.repository;

import com.khomsi.site_project.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT category FROM Category category WHERE category.enabled = true ORDER BY category.title ASC")
    public List<Category> findAllEnabled();

    @Query("select category from Category category where category.enabled = true and category.alias = ?1")
    public Category findByAliasEnabled(String alias);

    public Category findByAlias(String alias);

    public Long countById(Integer id);

    @Query("SELECT c FROM Category c WHERE c.title = :title")
    public Category findByTitle(@Param("title") String title);

    //For categories that doesn't have parents,
    // so we avoid error when we choose for parent category parent's id
    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    public List<Category> findRootCategories();

    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    public Page<Category> findRootCategories(Pageable pageable);
}
