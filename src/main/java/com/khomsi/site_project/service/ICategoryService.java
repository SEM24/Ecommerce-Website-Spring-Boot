package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Category;
import com.khomsi.site_project.exception.CategoryNotFoundException;

import java.util.List;

public interface ICategoryService {
    public List<Category> listAll();

    public List<Category> listCategoriesUserInForm();
//    public Category getCategory(String title) throws CategoryNotFoundException;
}
