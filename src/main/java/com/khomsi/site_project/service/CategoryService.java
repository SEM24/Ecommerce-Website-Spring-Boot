package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Category;
import com.khomsi.site_project.exception.CategoryNotFoundException;
import com.khomsi.site_project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

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
        List<Category> categoriesUserInForm = new ArrayList<>();

        Iterable<Category> categoriesInDB = categoryRep.findAll();

        for (Category category : categoriesInDB) {
            if (category.getParent() == null) {
                categoriesUserInForm.add(new Category(category.getTitle()));

                Set<Category> children = category.getChildren();

                for (Category subCat : children) {
                    String name = "--" + subCat.getTitle();
                    categoriesUserInForm.add(new Category(name));

                    listChildren(categoriesUserInForm, subCat, 1);
                }
            }
        }
        return categoriesUserInForm;
    }

    private void listChildren(List<Category> categoriesUserInForm, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;

        Set<Category> children = parent.getChildren();

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getTitle();
            categoriesUserInForm.add(new Category(name));
            listChildren(categoriesUserInForm, subCategory, newSubLevel);
        }
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRep.save(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoryRep.deleteById(id);
    }

    @Override
    public Category getCategory(Integer id) throws CategoryNotFoundException {
        try {
            return categoryRep.getReferenceById(id);
        } catch (NoSuchElementException e) {
            throw new CategoryNotFoundException("Couldn't find any category with id " + id);
        }
    }
}
