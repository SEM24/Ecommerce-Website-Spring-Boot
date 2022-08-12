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
        return categoryRep.findAll();
    }

    //FIXME re-write this method to new one
    @Override
    public List<Category> listCategoriesUserInForm() {
        List<Category> categoriesUserInForm = new ArrayList<>();

        Iterable<Category> categoriesInDB = categoryRep.findAll();

        for (Category category : categoriesInDB) {
            if (category.getParent() == null) {
                categoriesUserInForm.add(Category.copyIdAndTitle(category));

                Set<Category> children = category.getChildren();

                for (Category subCat : children) {
                    String name = "--" + subCat.getTitle();
                    categoriesUserInForm.add(Category.copyIdAndTitle(subCat.getId(), name));

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
            categoriesUserInForm.add(Category.copyIdAndTitle(subCategory.getId(), name));
            listChildren(categoriesUserInForm, subCategory, newSubLevel);
        }
    }

    //TODO test this method
    @Override
    public Category saveCategory(Category category) {
        Category parent = category.getParent();
        if (parent != null) {
            String allParentIds = parent.getAllParentsIDs() == null ? "-" : parent.getAllParentsIDs();
            allParentIds += String.valueOf(parent.getId()) + "-";
            category.setAllParentsIDs(allParentIds);
        }
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

    @Override
    public Category getCategoryByAlias(String alias) throws CategoryNotFoundException {
        Category category = categoryRep.findByAliasEnabled(alias);
        if (category == null) {
            throw new CategoryNotFoundException("Couldn't find any category with alias " + alias);
        }
        return category;
    }

    //list up parent of categories
    @Override
    public List<Category> getCategoryParents(Category child) {
        List<Category> listParents = new ArrayList<>();
        Category parent = child.getParent();

        //look up to parent by loop
        while (parent != null) {
            listParents.add(0, parent);
            parent = parent.getParent();
        }
        listParents.add(child);

        return listParents;
    }
}
