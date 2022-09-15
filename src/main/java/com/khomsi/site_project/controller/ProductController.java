package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.Category;
import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.exception.CategoryNotFoundException;
import com.khomsi.site_project.exception.ProductNotFoundException;
import com.khomsi.site_project.service.CategoryService;
import com.khomsi.site_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AdminTools adminTools;

    @GetMapping({"/category/{category_alias}"})
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

            adminTools.pageCountMethod(pageNum, model, pageProduct, startCount, endCount);

            model.addAttribute("pageTitle", category.getTitle());
            model.addAttribute("listCategoryParents", listCategoryParents);
            model.addAttribute("listProducts", listProducts);


            model.addAttribute("category", category);
            return "product/products_by_category";
        } catch (CategoryNotFoundException e) {
            model.addAttribute("error", e.getLocalizedMessage());
            return "error/404";
        }
    }

    @GetMapping("/product/{product_alias}")
    public String viewProductDetails(@PathVariable("product_alias") String alias, Model model) {
        try {
            Product product = productService.getProduct(alias);
            List<Category> listCategoryParents = categoryService.getCategoryParents(product.getCategory());
            model.addAttribute("listCategoryParents", listCategoryParents);
            model.addAttribute("product", product);
            return "product/product-page";
        } catch (ProductNotFoundException e) {
            model.addAttribute("error", e.getLocalizedMessage());
            return "error/404";
        }
    }

    @PostMapping("/products/check_unique")
    public @ResponseBody String checkUnique(@Param("id") Integer id, @Param("title") String title) {
        return productService.checkUnique(id, title);
    }

    @GetMapping("/search")
    public String searchFirstPage(@Param("keyword") String keyword, Model model) {
        return searchByPage(keyword, 1, model);
    }

    /*
     * We need param of page num, so i use pathVariable
     */
    @GetMapping("/search/page/{pageNum}")
    public String searchByPage(@Param("keyword") String keyword,
                               @PathVariable("pageNum") int pageNum,
                               Model model) {
        Page<Product> productsPage = productService.search(keyword, pageNum);
        List<Product> resultList = productsPage.getContent();

        long startCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PAGE + 1;
        long endCount = startCount + ProductService.SEARCH_RESULTS_PAGE - 1;
        adminTools.pageCountMethod(pageNum, model, productsPage, startCount, endCount);

        model.addAttribute("pageTitle", StringUtils.capitalize(keyword) + " - Search Result");
        model.addAttribute("keyword", keyword);
        model.addAttribute("resultList", resultList);
        return "product/search_result";
    }

}
