package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Category;

import java.util.List;

public interface ICategoryService {
    public List<Category> getAllCategories();

    public void saveCategory(Category category);

    public Category getCategory(int id);

    public void deleteCategory(int id);
}
