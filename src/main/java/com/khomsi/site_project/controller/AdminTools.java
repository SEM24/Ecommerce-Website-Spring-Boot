package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.*;
import com.khomsi.site_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AdminTools {
    @Autowired
    private UserService userService;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/users/check_login")
    public @ResponseBody String checkLoginUnique(@Param("id") Integer id, @Param("login") String login) {
        return userService.isLoginUnique(id, login);
    }

    /*
    Added this method to check unique login for user while he's registration
    You can modify it to have more unique fields.
    (if you need unique fields in admin panel for user, change another methods like isLoginUnique)
    */
    @PostMapping("/user/check")
    public @ResponseBody String checkLoginRegistration(@Param("login") String login) {
        return userService.checkLoginRegistration(login) ? "OK" : "Duplicate";
    }

    @PostMapping("/categories/check")
    public @ResponseBody String checkCategory(@Param("id") Integer id, @Param("title") String title) {
        return categoryService.checkCategoryTitle(id,title);
    }

    @PostMapping("/vendors/check")
    public @ResponseBody String checkVendor(@Param("id") Integer id, @Param("title") String title) {
        return vendorService.checkVendorTitle(id, title);
    }

    //Controller in admin panel for users
    @GetMapping("/admin/users/page/{pageNum}")
    public String listUsersByPage(@PathVariable(name = "pageNum") int pageNum, Model model) {
        Page<User> page = userService.listByPage(pageNum);
        List<User> listUsers = page.getContent();

        long startCount = (pageNum - 1) * UserService.USER_PER_PAGE + 1;
        long endCount = startCount + UserService.USER_PER_PAGE - 1;

        pageCountMethod(pageNum, model, page, startCount, endCount);

        model.addAttribute("users", listUsers);

        return "admin/user/users";
    }

    //Controller in admin panel for vendors
    @GetMapping("/admin/vendors/page/{pageNum}")
    public String listVendorsByPage(@PathVariable(name = "pageNum") int pageNum, Model model) {
        Page<Vendor> page = vendorService.listByPage(pageNum);
        List<Vendor> vendorList = page.getContent();

        long startCount = (pageNum - 1) * VendorService.VENDORS_PER_PAGE + 1;
        long endCount = startCount + VendorService.VENDORS_PER_PAGE - 1;

        pageCountMethod(pageNum, model, page, startCount, endCount);

        model.addAttribute("vendors", vendorList);

        return "admin/vendor/vendors";
    }

    //Controller in admin panel for orders
    @GetMapping("/admin/orders/page/{pageNum}")
    public String listOrdersByPage(@PathVariable(name = "pageNum") int pageNum, Model model) {
        Page<Order> page = ordersService.listByPage(pageNum);
        List<Order> orderList = page.getContent();

        long startCount = (pageNum - 1) * OrdersService.ORDERS_PER_PAGE + 1;
        long endCount = startCount + OrdersService.ORDERS_PER_PAGE - 1;

        pageCountMethod(pageNum, model, page, startCount, endCount);

        model.addAttribute("orders", orderList);

        return "admin/orders/orders";
    }

    //Controller in admin panel for products
    @GetMapping("/admin/products/page/{pageNum}")
    public String listProductsByPage(@PathVariable(name = "pageNum") int pageNum, Model model) {
        Page<Product> page = productService.listByPage(pageNum);
        List<Product> productList = page.getContent();

        long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_ADMIN_PAGE + 1;
        long endCount = startCount + ProductService.PRODUCTS_PER_ADMIN_PAGE - 1;

        pageCountMethod(pageNum, model, page, startCount, endCount);

        model.addAttribute("products", productList);

        return "admin/product/products";
    }

    //Controller in admin panel for categories
    @GetMapping("/admin/categories/page/{pageNum}")
    public String listCategoriesByPage(@PathVariable(name = "pageNum") int pageNum, Model model) {
        Page<Category> page = categoryService.listByPage(pageNum);
        List<Category> categoryList = page.getContent();

        long startCount = (pageNum - 1) * CategoryService.CATEGORIES_PER_PAGE + 1;
        long endCount = startCount + CategoryService.CATEGORIES_PER_PAGE - 1;

        pageCountMethod(pageNum, model, page, startCount, endCount);

        model.addAttribute("categories", categoryList);

        return "admin/category/categories";
    }

    public void pageCountMethod(@PathVariable("pageNum") int pageNum, Model model, Page<?> page,
                                long startCount, long endCount) {
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
    }
}
