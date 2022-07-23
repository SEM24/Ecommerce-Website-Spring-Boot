package com.khomsi.site_project.repository;

import com.khomsi.site_project.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
