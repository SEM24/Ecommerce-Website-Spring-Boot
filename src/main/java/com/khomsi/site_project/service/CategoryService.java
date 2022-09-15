package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Category;
import com.khomsi.site_project.exception.CategoryNotFoundException;
import com.khomsi.site_project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRep;

    //Shows only main categories with sub categories per page.
    //For ex if top categories is 2: Electronics, ..subcat., Clothes..subcat../next page
    public static final int TOP_CATEGORIES_PER_PAGE = 1;

    @Override
    public List<Category> listByPage(CategoryPageInfo pageInfo, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, TOP_CATEGORIES_PER_PAGE);

        Page<Category> pageCategories = categoryRep.findRootCategories(pageable);
        List<Category> rooCategories = pageCategories.getContent();
        pageInfo.setTotalElements(pageCategories.getTotalElements());
        pageInfo.setTotalPages(pageCategories.getTotalPages());

        return listHierarchicalCategories(rooCategories);
    }

    private List<Category> listHierarchicalCategories(List<Category> rootCategories) {
        List<Category> hierarchicalCategories = new ArrayList<>();

        for (Category rootCategory : rootCategories) {
            hierarchicalCategories.add(Category.copyFull(rootCategory));
            Set<Category> children = rootCategory.getChildren();

            for (Category subCategory : children) {
                String title = "--" + subCategory.getTitle();
                hierarchicalCategories.add(Category.copyFull(subCategory, title));
                listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1);
            }
        }
        return hierarchicalCategories;
    }

    private void listSubHierarchicalCategories(List<Category> hierarchicalCategories,
                                               Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getTitle();
            hierarchicalCategories.add(Category.copyFull(subCategory, name));

            //call the method itself to iterate each subCategory in subCategories
            listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel);
        }
    }

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

                    listSubCategoriesUsedInForm(categoriesUserInForm, subCat, 1);
                }
            }
        }
        return categoriesUserInForm;
    }

    private void listSubCategoriesUsedInForm(List<Category> categoriesUserInForm, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;

        Set<Category> children = parent.getChildren();

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getTitle();
            categoriesUserInForm.add(Category.copyIdAndTitle(subCategory.getId(), name));
            listSubCategoriesUsedInForm(categoriesUserInForm, subCategory, newSubLevel);
        }
    }

    @Override
    public Category saveCategory(Category category) {
        Category parent = category.getParent();
        if (parent != null) {
            String allParentIds = parent.getAllParentsIDs() == null ? "-" : parent.getAllParentsIDs();
            allParentIds += String.valueOf(parent.getId()) + "-";
            category.setAllParentsIDs(allParentIds);
        }
        if (category.getAlias() == null || category.getAlias().isEmpty()) {
            String defaultAlias = category.getTitle().toLowerCase();
            category.setAlias(convertCyrillic(defaultAlias).replaceAll(" ", "_"));
        } else {
            category.setAlias(category.getAlias().replaceAll(" ", "_").toLowerCase());
        }
        return categoryRep.save(category);
    }

    //Method to convert alias into english letters
    public String convertCyrillic(String message) {
        char[] abcCyr = {' ', 'а', 'б', 'в', 'г', 'д', 'і', 'е', 'ж', 'з', 'ѕ', 'и', 'ј', 'к', 'л', 'ґ', 'м', 'н', 'є',
                'о', 'п', 'р', 'с', 'т', 'ї', 'у', 'ф', 'х', 'ц', 'ч', 'џ', 'ш', 'А', 'Б', 'В', 'Г', 'Д', 'І', 'Е', 'Ж',
                'З', 'Ѕ', 'И', 'Ј', 'К', 'Л', 'Ґ', 'М', 'Н', 'Є', 'О', 'П', 'Р', 'С', 'Т', 'Ї', 'У', 'Ф', 'Х', 'Ц', 'Ч',
                'Џ', 'Ш', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/'};

        String[] abcLat = {" ", "a", "b", "v", "g", "d", "i", "e", "zh", "z", "y", "i", "j", "k", "l", "g", "m", "n", "e",
                "o", "p", "r", "s", "t", "ї", "u", "f", "h", "c", "ch", "x", "h", "A", "B", "V", "G", "D", "І", "E", "Zh",
                "Z", "Y", "I", "J", "K", "L", "G", "M", "N", "E", "O", "P", "R", "S", "T", "I", "U", "F", "H", "C", ":",
                "X", "{", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "_"};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            for (int x = 0; x < abcCyr.length; x++) {
                if (message.charAt(i) == abcCyr[x]) {
                    builder.append(abcLat[x]);
                }
            }
        }
        return builder.toString();
    }

    @Override
    public void deleteCategory(int id) throws CategoryNotFoundException {
        Long countById = categoryRep.countById(id);
        if (countById == null || countById == 0) {
            throw new CategoryNotFoundException("Couldn't find any category with id " + id);
        }
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

    //List up parent of categories
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

    @Override
    public String checkCategoryTitle(Integer id, String title, String alias) {
        Category categoryByTitle = categoryRep.findByTitle(title);
        boolean isCreatingNew = (id == null || id == 0);

        if (isCreatingNew) {
            if (categoryByTitle != null) {
                return "DuplicateTitle";
            } else {
                Category categoryByAlias = categoryRep.findByAlias(alias);
                if (categoryByAlias != null) {
                    return "DuplicateAlias";
                }
            }
        } else {
            if (categoryByTitle != null && !Objects.equals(categoryByTitle.getId(), id)) {
                return "DuplicateTitle";
            }
            Category categoryByAlias = categoryRep.findByAlias(alias);
            if (categoryByAlias != null && !Objects.equals(categoryByAlias.getId(), id)) {
                return "DuplicateAlias";
            }
        }
        return "OK";
    }

}
