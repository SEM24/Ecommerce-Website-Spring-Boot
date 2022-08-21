package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.Category;
import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.exception.CategoryNotFoundException;
import com.khomsi.site_project.exception.ProductNotFoundException;
import com.khomsi.site_project.service.CategoryService;
import com.khomsi.site_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category/{category_alias}")
    public String viewCategoryFirstPage(@PathVariable("category_alias") String alias,
                                        Model model) {
        return viewCategoryByPage(alias, model, 1);
    }

    @GetMapping("/category/{category_alias}/page/{pageNum}")
    public String viewCategoryByPage(@PathVariable("category_alias") String alias,
                                     Model model, @PathVariable("pageNum") int pageNum) {
        try {
            Category category = categoryService.getCategoryByAlias(alias);

            List<Category> listCategoryParents = categoryService.getCategoryParents(category);
            Page<Product> pageProduct = productService.listByCategory(pageNum, category.getId());

            List<Product> listProducts = pageProduct.getContent();
            long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
            long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
            if (endCount > pageProduct.getTotalElements()) {
                endCount = pageProduct.getTotalElements();
            }
            model.addAttribute("currentPage", pageNum);
            model.addAttribute("totalPages", pageProduct.getTotalPages());
            model.addAttribute("startCount", startCount);
            model.addAttribute("endCount", endCount);
            model.addAttribute("totalItems", pageProduct.getTotalElements());

            model.addAttribute("pageTitle", category.getTitle());
            model.addAttribute("listCategoryParents", listCategoryParents);
            model.addAttribute("listProducts", listProducts);

            model.addAttribute("category", category);

            return "products_by_category";
        } catch (CategoryNotFoundException e) {
            return "error/404";
        }
    }

    //TODO нужно сделать уникальные алиасы по туториалу в папке продукты
    @GetMapping("/product/{product_alias}")
    public String viewProductDetails(@PathVariable("product_alias") String alias, Model model) {
        try {
            Product product = productService.getProduct(alias);
            List<Category> listCategoryParents = categoryService.getCategoryParents(product.getCategory());
            model.addAttribute("listCategoryParents", listCategoryParents);
            model.addAttribute("product", product);
            return "product-details";
        } catch (ProductNotFoundException e) {
            return "error/404";
        }
    }
}
