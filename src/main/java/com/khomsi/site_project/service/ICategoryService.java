package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Category;
import com.khomsi.site_project.exception.CategoryNotFoundException;

import java.util.List;

public interface ICategoryService {
    public List<Category> listAll();

    public List<Category> listCategoriesUserInForm();
    public Category saveCategory(Category category);
//    public void listChildren(List<Category> categoriesUserInForm, Category parent, int subLevel);

    public void deleteCategory(int id);

    public Category getCategory(Integer id) throws CategoryNotFoundException;
    public Category getCategoryByAlias(String alias) throws CategoryNotFoundException;

    //list up parent of categories
    List<Category> getCategoryParents(Category child);
}
