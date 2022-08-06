package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Category;
import com.khomsi.site_project.exception.CategoryNotFoundException;

public interface ICategoryService {
    public Category getCategory(String title) throws CategoryNotFoundException;
}
