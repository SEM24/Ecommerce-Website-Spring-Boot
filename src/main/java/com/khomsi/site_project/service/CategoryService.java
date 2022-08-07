package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Category;
import com.khomsi.site_project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRep;

    @Override
    public List<Category> listAll() {
        return (List<Category>) categoryRep.findAll();
    }

    @Override
    public List<Category> listCategoriesUserInForm() {
        return (List<Category>) categoryRep.findAll();
    }

    public Category saveCategory(Category category) {
        return categoryRep.save(category);
    }

    public void deleteCategory(int id){
        categoryRep.deleteById(id);
    }
//    @Override
//    public Category getCategory(String title) throws CategoryNotFoundException {
//        Category category = categoryRep.findByTitleEnabled(title);
//        if (category == null)
//            throw new CategoryNotFoundException("Couldn't find any category with title " + title);
//
//        return category;
//
//    }
}
